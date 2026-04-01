/*
 * Copyright 2026 Enaium
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

package cn.enaium.onekeyminer.utility

import cn.enaium.onekeyminer.common.BlockPos
import cn.enaium.onekeyminer.common.Player
import cn.enaium.onekeyminer.common.Direction
import cn.enaium.onekeyminer.config.OneKeyMinerConfig
import cn.enaium.onekeyminer.config.Shape
import java.util.*
import kotlin.math.abs
import kotlin.math.cbrt

/**
 * @author Enaium
 */
fun BlockPos.findBlock(player: Player): Set<BlockPos> {
    val result = mutableSetOf<BlockPos>()
    val queue = ArrayDeque<BlockPos>()
    val visited = mutableSetOf<BlockPos>()

    queue.add(this)

    while (!queue.isEmpty() && result.size < OneKeyMinerConfig.limit.value) {
        val currentPos = queue.poll()

        if (currentPos.name == this.name) {
            result.add(currentPos)
            visited.add(currentPos)

            when (OneKeyMinerConfig.shape.value) {
                Shape.CUBE -> {
                    for (xOffset in -1..1) {
                        for (yOffset in -1..1) {
                            for (zOffset in -1..1) {
                                val adjacentPos = currentPos.add(xOffset, yOffset, zOffset)
                                if (!visited.contains(adjacentPos)) {
                                    queue.offer(adjacentPos)
                                    visited.add(adjacentPos)
                                }
                            }
                        }
                    }
                }

                Shape.HORIZONTAL_PLANE -> {
                    for (xOffset in -1..1) {
                        for (zOffset in -1..1) {
                            val adjacentPos = currentPos.add(xOffset, 0, zOffset)
                            if (!visited.contains(adjacentPos)) {
                                queue.offer(adjacentPos)
                                visited.add(adjacentPos)
                            }
                        }
                    }
                }

                Shape.VERTICAL_PLANE -> {
                    if (player.direction in listOf(Direction.NORTH, Direction.SOUTH)) {
                        for (xOffset in -1..1) {
                            for (yOffset in -1..1) {
                                val adjacentPos = currentPos.add(xOffset, yOffset, 0)
                                if (!visited.contains(adjacentPos)) {
                                    queue.offer(adjacentPos)
                                    visited.add(adjacentPos)
                                }
                            }
                        }
                    } else if (player.direction in listOf(Direction.WEST, Direction.EAST)) {
                        for (zOffset in -1..1) {
                            for (yOffset in -1..1) {
                                val adjacentPos = currentPos.add(0, yOffset, zOffset)
                                if (!visited.contains(adjacentPos)) {
                                    queue.offer(adjacentPos)
                                    visited.add(adjacentPos)
                                }
                            }
                        }
                    }
                }

                Shape.COLUMN -> {
                    for (yOffset in -1..1) {
                        val adjacentPos = currentPos.add(0, yOffset, 0)
                        if (!visited.contains(adjacentPos)) {
                            queue.offer(adjacentPos)
                            visited.add(adjacentPos)
                        }
                    }
                }

                Shape.SPHERE -> {
                    for (xOffset in -1..1) {
                        for (yOffset in -1..1) {
                            for (zOffset in -1..1) {
                                if (abs(xOffset) + abs(yOffset) + abs(zOffset) <= 2) {
                                    val adjacentPos = currentPos.add(xOffset, yOffset, zOffset)
                                    if (!visited.contains(adjacentPos)) {
                                        queue.offer(adjacentPos)
                                        visited.add(adjacentPos)
                                    }
                                }
                            }
                        }
                    }
                }

                Shape.TUNNEL -> {
                    if (player.direction in listOf(Direction.NORTH, Direction.SOUTH)) {
                        for (xOffset in -1..1) {
                            for (yOffset in -1..1) {
                                for (zOffset in -1..1) {
                                    val adjacentPos = currentPos.add(xOffset, yOffset, zOffset)
                                    if (abs(adjacentPos.x - this.x) <= 1 && abs(adjacentPos.y - this.y) <= 1) {
                                        if (!visited.contains(adjacentPos)) {
                                            queue.offer(adjacentPos)
                                            visited.add(adjacentPos)
                                        }
                                    }
                                }
                            }
                        }
                    } else if (player.direction in listOf(Direction.WEST, Direction.EAST)) {
                        for (xOffset in -1..1) {
                            for (yOffset in -1..1) {
                                for (zOffset in -1..1) {
                                    val adjacentPos = currentPos.add(xOffset, yOffset, zOffset)
                                    if (abs(adjacentPos.y - this.y) <= 1 && abs(adjacentPos.z - this.z) <= 1) {
                                        if (!visited.contains(adjacentPos)) {
                                            queue.offer(adjacentPos)
                                            visited.add(adjacentPos)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Shape.PYRAMID -> {
                    val maxHeight = computePyramidHeight(OneKeyMinerConfig.limit.value)

                    for (xOffset in -1..1) {
                        for (yOffset in -1..1) {
                            for (zOffset in -1..1) {
                                val adjacentPos = currentPos.add(xOffset, yOffset, zOffset)

                                val dx = abs(adjacentPos.x - this.x)
                                val dy = adjacentPos.y - this.y
                                val dz = abs(adjacentPos.z - this.z)

                                if (dy in 0..maxHeight) {
                                    val radius = maxHeight - dy

                                    if (dx <= radius && dz <= radius) {
                                        if (!visited.contains(adjacentPos)) {
                                            queue.offer(adjacentPos)
                                            visited.add(adjacentPos)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Shape.DIAMOND -> {
                    for (xOffset in -2..2) {
                        for (yOffset in -2..2) {
                            for (zOffset in -2..2) {
                                if (abs(xOffset) + abs(yOffset) + abs(zOffset) <= 2) {
                                    val adjacentPos = currentPos.add(xOffset, yOffset, zOffset)
                                    if (!visited.contains(adjacentPos)) {
                                        queue.offer(adjacentPos)
                                        visited.add(adjacentPos)
                                    }
                                }
                            }
                        }
                    }
                }

                Shape.CONE -> {
                    val (dx, dz) = when (player.direction) {
                        Direction.NORTH -> 0 to -1
                        Direction.SOUTH -> 0 to 1
                        Direction.WEST -> -1 to 0
                        Direction.EAST -> 1 to 0
                        else -> return emptySet()
                    }

                    for (i in 1..2) {
                        for (x in -i..i) {
                            for (y in -i..i) {
                                val adjacentPos = currentPos.add(dx * i + x, y, dz * i + x)
                                if (!visited.contains(adjacentPos)) {
                                    queue.offer(adjacentPos)
                                    visited.add(adjacentPos)
                                }
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

fun computePyramidHeight(limit: Int): Int {
    var h = cbrt(limit * 0.75).toInt()

    fun calc(h: Int): Int {
        return (h + 1) * (2 * h + 1) * (2 * h + 3) / 3
    }

    while (calc(h) > limit) h--
    while (calc(h + 1) <= limit) h++

    return h
}