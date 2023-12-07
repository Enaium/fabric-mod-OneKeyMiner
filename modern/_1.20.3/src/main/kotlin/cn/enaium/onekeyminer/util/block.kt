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
package cn.enaium.onekeyminer.util

import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

/**
 * @author Enaium
 */
fun getName(identifier: Identifier): String {
    return identifier.namespace + ":" + identifier.path.substring(identifier.path.lastIndexOf("/") + 1)
}

fun getName(world: World, blockPos: BlockPos): String {
    return getName(world.getBlockState(blockPos).block.lootTableId)
}

/**
 * Finds blocks in a server world within a certain radius limit, relative to a center position.
 *
 * @param world     The server world to search in
 * @param centerPos The center position to search around
 * @param limit     The maximum number of blocks to search for
 * @return A set of BlockPos objects representing the found blocks
 */
fun findBlocks(world: World, centerPos: BlockPos, limit: Int): Set<BlockPos> {
    // Create a HashSet to hold the BlockPos objects representing found blocks
    val blockList: MutableSet<BlockPos> = HashSet()
    // Get the name of the block at the center position
    val centerBlockName = getName(world, centerPos)
    // Create a LinkedList to function as a queue for the breadth-first search algorithm
    val queue: Queue<BlockPos> = LinkedList()
    // Create a HashSet to hold visited positions to prevent revisiting them during the search
    val visited: MutableSet<BlockPos> = HashSet()
    // Add the center position to the queue to start the search
    queue.offer(centerPos)

    // Loop while the queue is not empty and the limit on found blocks has not been reached
    while (!queue.isEmpty() && blockList.size < limit) {
        // Save the current size of the queue to process all positions added during the previous iteration of the loop
        val queueSize = queue.size
        // Loop through all positions added during the previous iteration of the loop, and stop if the limit on found blocks has been reached
        var i = 0
        while (i < queueSize && blockList.size < limit) {

            // Retrieve and remove the first position in the queue
            val currentPos = queue.poll()
            // If the block at the current position matches the center block, add it to the list of found blocks and mark it as visited
            if (getName(world, currentPos) == centerBlockName) {
                blockList.add(currentPos)
                visited.add(currentPos)

                // Check all adjacent blocks for new positions to search
                for (xOffset in -1..1) {
                    for (yOffset in -1..1) {
                        for (zOffset in -1..1) {
                            // Ignore the current position and only add unvisited adjacent positions
                            if (xOffset != 0 || yOffset != 0 || zOffset != 0) {
                                assert(currentPos != null)
                                val adjacentPos = currentPos!!.add(xOffset, yOffset, zOffset)
                                if (!visited.contains(adjacentPos)) {
                                    queue.offer(adjacentPos)
                                    visited.add(adjacentPos)
                                }
                            }
                        }
                    }
                }
            } else {
                // If the block at the current position does not match the center block, mark it as visited without adding it to the found list
                visited.add(currentPos)
            }
            i++
        }
    }
    // Return the set of found blocks
    return blockList
}
