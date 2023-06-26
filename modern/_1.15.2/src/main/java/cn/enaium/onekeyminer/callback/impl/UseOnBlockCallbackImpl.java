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
import cn.enaium.onekeyminer.callback.UseOnBlockCallback;
import cn.enaium.onekeyminer.util.BlockUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.MiningToolItem;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * @author Enaium
 */
public class UseOnBlockCallbackImpl implements UseOnBlockCallback {
    @Override
    public void interact(PlayerEntity player, World world, ItemStack stack, Hand hand, BlockHitResult hitResult) {
        boolean canMine = stack.getItem().canMine(world.getBlockState(hitResult.getBlockPos()), world, hitResult.getBlockPos(), player);
        Config.Model config = Config.getModel();
        if (canMine && config.interact && (stack.getItem() instanceof MiningToolItem || stack.getItem() instanceof HoeItem) && player.isSneaking()) {
            for (BlockPos block : BlockUtil.findBlocks(world, hitResult.getBlockPos(), config.limit)) {
                stack.getItem().useOnBlock(new ItemUsageContext(player, hand, new BlockHitResult(new Vec3d(block.getX() + 0.5, block.getY() + 0.5, block.getZ() + 0.5), Direction.UP, block, false)));
            }
        }
    }
}
