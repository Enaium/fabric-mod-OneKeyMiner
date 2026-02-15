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

import cn.enaium.onekeyminer.Config
import cn.enaium.onekeyminer.common.Action
import cn.enaium.onekeyminer.common.Tool
import cn.enaium.onekeyminer.event.ServerCommandCallbacks

/**
 * @author Enaium
 */
class ActionCallbackImpl : ServerCommandCallbacks.ActionCallback {
    override fun execute(
        tool: Tool,
        action: Action,
        block: String
    ) {
        val block = block.let {
            if (!block.contains(":")) {
                "minecraft:$it"
            } else {
                it
            }
        }
        when (tool) {
            Tool.AXE -> {
                when (action) {
                    Action.ADD -> Config.model.axe.add(block)
                    Action.REMOVE -> Config.model.axe.removeIf { s: String -> s == block }
                }
            }

            Tool.HOE -> {
                when (action) {
                    Action.ADD -> Config.model.hoe.add(block)
                    Action.REMOVE -> Config.model.hoe.removeIf { s: String -> s == block }
                }
            }

            Tool.PICKAXE -> {
                when (action) {
                    Action.ADD -> Config.model.pickaxe.add(block)
                    Action.REMOVE -> Config.model.pickaxe.removeIf { s: String -> s == block }
                }
            }

            Tool.SHOVEL -> {
                when (action) {
                    Action.ADD -> Config.model.shovel.add(block)
                    Action.REMOVE -> Config.model.shovel.removeIf { s: String -> s == block }
                }
            }

            Tool.SHEARS -> {
                when (action) {
                    Action.ADD -> Config.model.shears.add(block)
                    Action.REMOVE -> Config.model.shears.removeIf { s: String -> s == block }
                }
            }

            Tool.ANY -> {
                when (action) {
                    Action.ADD -> Config.model.any.add(block)
                    Action.REMOVE -> Config.model.any.removeIf { s: String -> s == block }
                }
            }
        }
    }
}