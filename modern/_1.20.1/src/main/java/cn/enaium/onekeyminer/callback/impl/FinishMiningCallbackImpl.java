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

package cn.enaium.onekeyminer.callback.impl;

import cn.enaium.onekeyminer.Config;
import cn.enaium.onekeyminer.callback.FinishMiningCallback;
import cn.enaium.onekeyminer.util.BlockUtil;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Enaium
 */
public class FinishMiningCallbackImpl implements FinishMiningCallback {
    @Override
    public ActionResult interact(ServerWorld world, ServerPlayerEntity player, BlockPos pos, Function<BlockPos, Void> tryBreak) {
        ItemStack stack = player.getInventory().getStack(player.getInventory().selectedSlot);
        if (stack != null) {
            var canMine = stack.getItem().canMine(world.getBlockState(pos), world, pos, player);
            if (canMine && (stack.getItem() instanceof MiningToolItem || stack.getItem() instanceof ShearsItem) && player.isSneaking()) {
                var config = Config.getModel();
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
                } else if (stack.getItem() instanceof MiningToolItem) {
                    list.addAll(config.any);
                }
                final var name = BlockUtil.getName(world, pos);
                if (list.contains(name)) {
                    BlockUtil.findBlocks(world, pos, config.limit).forEach(tryBreak::apply);
                }
            }
        }
        return ActionResult.SUCCESS;
    }
}
