/**
 * Copyright 2022 Enaium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.enaium.onekeyminer.mixin;

import cn.enaium.onekeyminer.OneKeyMiner;
import cn.enaium.onekeyminer.model.Config;
import cn.enaium.onekeyminer.util.BlockUtil;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.*;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayerInteractionManagerMixin {
    @Shadow
    public abstract boolean tryBreakBlock(BlockPos pos);

    @Shadow public ServerPlayerEntity player;
    private final List<BlockPos> searched = new ArrayList<>();

    @Inject(at = @At(value = "HEAD"), method = "finishMining")
    private void finishMining(BlockPos pos, PlayerActionC2SPacket.Action action, String reason, CallbackInfo ci) {

        ItemStack stack = player.inventory.getStack(player.inventory.selectedSlot);
        if (stack != null) {
            boolean canMine = stack.getItem().canMine(BlockUtil.getBlockState(pos), MinecraftClient.getInstance().world, pos, player);
            if (canMine && (stack.getItem() instanceof MiningToolItem || stack.getItem() instanceof ShearsItem) && player.isSneaking()) {
                Config config = OneKeyMiner.config;
                List<String> list = new ArrayList<>();
                if (stack.getItem() instanceof AxeItem) {
                    list.addAll(config.axe);
                } else if (stack.getItem() instanceof HoeItem) {
                    list.addAll(config.hoe);
                } else if (stack.getItem() instanceof PickaxeItem) {
                    list.addAll(config.pickaxe);
                } else if (stack.getItem() instanceof ShovelItem) {
                    list.addAll(config.shovel);
                } else if (stack.getItem() instanceof ShearsItem) {
                    list.addAll(config.shears);
                }

                searched.clear();
                breakBreakBlock(pos, config.limit, list);
            }
        }
    }


    private void breakBreakBlock(BlockPos blockPos, int limit, List<String> blocks) {
        int radius = 1;
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos newBlockPos = new BlockPos(blockPos.getX() + x, blockPos.getY() + y, blockPos.getZ() + z);
                    BlockState blockState = BlockUtil.getBlockState(newBlockPos);
                    String name = BlockUtil.getName(blockState.getBlock().getLootTableId());

                    if (blocks.contains(name) && !searched.contains(newBlockPos) && searched.size() < limit) {
                        searched.add(newBlockPos);


                        tryBreakBlock(newBlockPos);
                        breakBreakBlock(newBlockPos, limit, blocks);
                    }
                }
            }
        }
    }
}
