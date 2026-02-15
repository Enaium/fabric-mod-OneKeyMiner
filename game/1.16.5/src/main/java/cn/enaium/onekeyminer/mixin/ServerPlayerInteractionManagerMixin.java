/*
 * Copyright 2022 Enaium
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static cn.enaium.onekeyminer.utility.UtilityKt.toCommon;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayerInteractionManagerMixin {
    @Shadow
    public ServerPlayerEntity player;
    @Shadow
    public ServerWorld world;

    @Shadow
    public abstract boolean tryBreakBlock(BlockPos pos);

    @Inject(at = @At(value = "HEAD"), method = "finishMining")
    private void finishMining(BlockPos pos, PlayerActionC2SPacket.Action action, String reason, CallbackInfo ci) {
        ServerPlayerCallbacks.FinishMiningCallback.Companion.getEVENT().getInvoker().interact(
                toCommon(player, world), toCommon(pos, world),
                (blockPos) -> tryBreakBlock(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()))
        );
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;useOnBlock(Lnet/minecraft/item/ItemUsageContext;)Lnet/minecraft/util/ActionResult;"), method = "interactBlock")
    public void interactBlock(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        ServerPlayerCallbacks.UseOnBlockCallback.Companion.getEVENT().getInvoker().interact(toCommon(player, world), toCommon(hitResult.getBlockPos(), world), (blockPos -> {
            stack.getItem().useOnBlock(new ItemUsageContext(player, hand, new BlockHitResult(new Vec3d(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5), hitResult.getSide(), new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()), false)));
            return null;
        }));
    }
}
