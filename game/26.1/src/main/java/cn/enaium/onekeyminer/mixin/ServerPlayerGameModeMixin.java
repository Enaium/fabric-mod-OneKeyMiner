/*
 * Copyright 2026 Enaium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.enaium.onekeyminer.mixin;

import cn.enaium.onekeyminer.event.ServerPlayerCallbacks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static cn.enaium.onekeyminer.utility.UtilityKt.toCommon;

/**
 * @author Enaium
 */
@Mixin(ServerPlayerGameMode.class)
public abstract class ServerPlayerGameModeMixin {
    @Shadow
    protected ServerLevel level;

    @Shadow
    @Final
    protected ServerPlayer player;

    @Shadow
    public abstract boolean destroyBlock(BlockPos pos);

    @Inject(at = @At(value = "HEAD"), method = "destroyAndAck")
    private void finishMining(BlockPos pos, int sequence, String exitId, CallbackInfo ci) {
        ServerPlayerCallbacks.FinishMiningCallback.Companion.getEVENT().getInvoker().interact(
                toCommon(player, level), toCommon(pos, level),
                (blockPos) -> destroyBlock(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()))
        );
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;useItemOn(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;"), method = "useItemOn")
    public void interactBlock(ServerPlayer player, Level level, ItemStack itemStack, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
        ServerPlayerCallbacks.UseOnBlockCallback.Companion.getEVENT().getInvoker().interact(toCommon(player, level), toCommon(hitResult.getBlockPos(), level), (blockPos -> {
            itemStack.getItem().useOn(new UseOnContext(player, hand, new BlockHitResult(new Vec3(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5), hitResult.getDirection(), new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()), false)));
            return null;
        }));
    }
}
