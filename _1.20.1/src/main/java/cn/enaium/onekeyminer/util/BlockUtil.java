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

package cn.enaium.onekeyminer.util;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * @author Enaium
 */
public class BlockUtil {
    public static String getName(Identifier identifier) {
        return identifier.getNamespace() + ":" + identifier.getPath().substring(identifier.getPath().lastIndexOf("/") + 1);
    }

    public static String getName(World world, BlockPos blockPos) {
        return getName(world.getBlockState(blockPos).getBlock().getLootTableId());
    }

    /**
     * Finds blocks in a server world within a certain radius limit, relative to a center position.
     *
     * @param world     The server world to search in
     * @param centerPos The center position to search around
     * @param limit     The maximum number of blocks to search for
     * @return A set of BlockPos objects representing the found blocks
     */
    public static Set<BlockPos> findBlocks(World world, BlockPos centerPos, int limit) {
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
}
