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

package cn.enaium.onekeyminer.utility

import cn.enaium.onekeyminer.common.BlockPos
import java.util.*

/**
 * @author Enaium
 */
fun BlockPos.findBlock(limit: Int): Set<BlockPos> {
    val result = mutableSetOf<BlockPos>()
    val queue = ArrayDeque<BlockPos>()
    val visited = mutableSetOf<BlockPos>()
    queue.add(this)
    while (!queue.isEmpty() && result.size < limit) {
        val currentPos = queue.poll()
        if (currentPos.name == this.name) {
            result.add(currentPos)
            visited.add(currentPos)
            for (xOffset in -1..1) {
                for (yOffset in -1..1) {
                    for (zOffset in -1..1) {
                        if (xOffset != 0 || yOffset != 0 || zOffset != 0) {
                            val adjacentPos = currentPos.add(xOffset, yOffset, zOffset)
                            if (!visited.contains(adjacentPos)) {
                                queue.offer(adjacentPos)
                                visited.add(adjacentPos)
                            }
                        }
                    }
                }
            }
        } else {
            visited.add(currentPos)
        }
    }
    return result
}