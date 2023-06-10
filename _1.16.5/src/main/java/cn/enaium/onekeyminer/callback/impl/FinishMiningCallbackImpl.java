/*
 * A minecraft mod for Fabric that allows you to mine blocks with one mining action.
 * Copyright (C) 2023  Enaium
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
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

import java.util.*;
import java.util.function.Function;

/**
 * @author Enaium
 */
public class FinishMiningCallbackImpl implements FinishMiningCallback {
    /**
     * Finds blocks in a server world within a certain radius limit, relative to a center position.
     *
     * @param world     The server world to search in
     * @param centerPos The center position to search around
     * @param limit     The maximum number of blocks to search for
     * @return A set of BlockPos objects representing the found blocks
     */
    public static Set<BlockPos> findBlocks(ServerWorld world, BlockPos centerPos, int limit) {
        // Create a HashSet to hold the BlockPos objects representing found blocks
        Set<BlockPos> blockList = new HashSet<>();
        // Get the name of the block at the center position
        String centerBlockName = BlockUtil.getName(world, centerPos);
        // Create a LinkedList to function as a queue for the breadth-first search algorithm
        Queue<BlockPos> queue = new LinkedList<>();
        // Create a HashSet to hold visited positions to prevent revisiting them during the search
        Set<BlockPos> visited = new HashSet<>();
        // Add the center position to the queue to start the search
        queue.offer(centerPos);

        // Loop while the queue is not empty and the limit on found blocks has not been reached
        while (!queue.isEmpty() && blockList.size() < limit) {
            // Save the current size of the queue to process all positions added during the previous iteration of the loop
            int queueSize = queue.size();
            // Loop through all positions added during the previous iteration of the loop, and stop if the limit on found blocks has been reached
            for (int i = 0; i < queueSize && blockList.size() < limit; i++) {
                // Retrieve and remove the first position in the queue
                BlockPos currentPos = queue.poll();
                // If the block at the current position matches the center block, add it to the list of found blocks and mark it as visited
                if (BlockUtil.getName(world, currentPos).equals(centerBlockName)) {
                    blockList.add(currentPos);
                    visited.add(currentPos);

                    // Check all adjacent blocks for new positions to search
                    for (int xOffset = -1; xOffset <= 1; xOffset++) {
                        for (int yOffset = -1; yOffset <= 1; yOffset++) {
                            for (int zOffset = -1; zOffset <= 1; zOffset++) {
                                // Ignore the current position and only add unvisited adjacent positions
                                if (xOffset != 0 || yOffset != 0 || zOffset != 0) {
                                    assert currentPos != null;
                                    BlockPos adjacentPos = currentPos.add(xOffset, yOffset, zOffset);
                                    if (!visited.contains(adjacentPos)) {
                                        queue.offer(adjacentPos);
                                        visited.add(adjacentPos);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    // If the block at the current position does not match the center block, mark it as visited without adding it to the found list
                    visited.add(currentPos);
                }
            }
        }
        // Return the set of found blocks
        return blockList;
    }

    @Override
    public ActionResult interact(ServerWorld world, ServerPlayerEntity player, BlockPos pos, Function<BlockPos, Void> tryBreak) {
        ItemStack stack = player.inventory.getStack(player.inventory.selectedSlot);
        if (stack != null) {
            boolean canMine = stack.getItem().canMine(world.getBlockState(pos), world, pos, player);
            if (canMine && (stack.getItem() instanceof MiningToolItem || stack.getItem() instanceof ShearsItem) && player.isSneaking()) {
                Config.Model config = Config.getModel();
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
                final String name = BlockUtil.getName(world, pos);
                if (list.contains(name)) {
                    findBlocks(world, pos, config.limit).forEach(tryBreak::apply);
                }
            }
        }
        return ActionResult.SUCCESS;
    }
}
