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

package cn.enaium.onekeyminer.event.impl

import cn.enaium.onekeyminer.Config.model
import cn.enaium.onekeyminer.common.BlockPos
import cn.enaium.onekeyminer.common.Player
import cn.enaium.onekeyminer.common.Tool
import cn.enaium.onekeyminer.event.ServerPlayerCallbacks
import cn.enaium.onekeyminer.utility.findBlock

/**
 * @author Enaium
 */
abstract class FinishMiningCallbackImpl : ServerPlayerCallbacks.FinishMiningCallback {
    override fun interact(player: Player, pos: BlockPos, tryBreak: (BlockPos) -> Boolean) {
        if (!player.canMine(pos) || !condition(player)) return
        val list = mutableListOf<String>()

        when (player.handTool) {
            Tool.AXE -> {
                list.addAll(model.axe)
            }

            Tool.HOE -> {
                list.addAll(model.hoe)
            }

            Tool.PICKAXE -> {
                list.addAll(model.pickaxe)
            }

            Tool.SHOVEL -> {
                list.addAll(model.shovel)
            }

            Tool.SHEARS -> {
                list.addAll(model.shears)
            }

            Tool.ANY -> {
                list.addAll(model.any)
            }
        }

        if (pos.name in list) {
            pos.findBlock(64).forEach { block ->
                player.mainHandEmpty && return
                tryBreak(block)
            }
        }
    }

    abstract fun condition(player: Player): Boolean
}