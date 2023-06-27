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

import cn.enaium.onekeyminer.callback.FinishMiningCallback;
import cn.enaium.onekeyminer.callback.UseOnBlockCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayerInteractionManagerMixin {
    @Shadow
    public World world;

    @Shadow
    public abstract boolean method_10766(BlockPos pos);

    @Shadow
    public ServerPlayerEntity player;

    @Inject(at = @At("HEAD"), method = "method_10764")
    public void finishMining(BlockPos blockPos, CallbackInfo ci) {
        FinishMiningCallback.Companion.getEVENT().invoker().interact(world, player, blockPos, (tryBreak) -> {
            method_10766(tryBreak);
            return null;
        });
    }

    @Inject(at = @At("HEAD"), method = "interactBlock")
    public void method_12792(PlayerEntity player, World world, ItemStack stack, BlockPos pos, Direction direction, float x, float y, float z, CallbackInfoReturnable<Boolean> cir) {
        UseOnBlockCallback.Companion.getEVENT().invoker().interact(stack, player, world, pos, direction);
    }
}
