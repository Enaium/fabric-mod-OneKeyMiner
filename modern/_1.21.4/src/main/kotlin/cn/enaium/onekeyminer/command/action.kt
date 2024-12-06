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
package cn.enaium.onekeyminer.command

import cn.enaium.onekeyminer.Config
import cn.enaium.onekeyminer.ROOT
import cn.enaium.onekeyminer.enums.Action
import cn.enaium.onekeyminer.enums.Tool
import cn.enaium.onekeyminer.util.getName
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.command.argument.BlockStateArgument
import net.minecraft.command.argument.BlockStateArgumentType
import net.minecraft.registry.Registries
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.HoverEvent
import net.minecraft.text.Style
import net.minecraft.text.Text
import net.minecraft.util.Formatting

/**
 * @author Enaium
 */
fun actionCommand(dispatcher: CommandDispatcher<ServerCommandSource>, registryAccess: CommandRegistryAccess) {
    for (tool in Tool.entries) {
        for (action in Action.entries) {
            dispatcher.register(
                ROOT.then(
                    CommandManager.literal(tool.name).then(
                        CommandManager.literal(action.name).then(
                            CommandManager.argument(
                                "block",
                                BlockStateArgumentType.blockState(registryAccess)
                            ).executes { context: CommandContext<ServerCommandSource> ->
                                val block = context.getArgument("block", BlockStateArgument::class.java)
                                val itemStack = block.blockState.block.asItem().defaultStack
                                val blockName: String = Registries.BLOCK.getEntry(block.blockState.block).idAsString
                                when (action) {
                                    Action.ADD -> {
                                        when (tool) {
                                            Tool.AXE -> Config.model.axe.add(blockName)
                                            Tool.HOE -> Config.model.hoe.add(blockName)
                                            Tool.PICKAXE -> Config.model.pickaxe.add(blockName)
                                            Tool.SHOVEL -> Config.model.shovel.add(blockName)
                                            Tool.SHEARS -> Config.model.shears.add(blockName)
                                            Tool.ANY -> Config.model.any.add(blockName)
                                        }
                                        context.source.sendFeedback({
                                            Text.translatable(
                                                "command.action.add.success",
                                                Text.literal(blockName).styled { style: Style ->
                                                    style.withColor(Formatting.AQUA).withHoverEvent(
                                                        HoverEvent(
                                                            HoverEvent.Action.SHOW_ITEM,
                                                            HoverEvent.ItemStackContent(itemStack)
                                                        )
                                                    )
                                                }
                                            )
                                        }, false)
                                    }

                                    Action.REMOVE -> {
                                        when (tool) {
                                            Tool.AXE -> Config.model.axe.removeIf { s: String -> s == blockName }
                                            Tool.HOE -> Config.model.hoe.removeIf { s: String -> s == blockName }
                                            Tool.PICKAXE -> Config.model.pickaxe.removeIf { s: String -> s == blockName }
                                            Tool.SHOVEL -> Config.model.shovel.removeIf { s: String -> s == blockName }
                                            Tool.SHEARS -> Config.model.shears.removeIf { s: String -> s == blockName }
                                            Tool.ANY -> Config.model.any.removeIf { s: String -> s == blockName }
                                        }
                                        context.source.sendFeedback({
                                            Text.translatable(
                                                "command.action.remove.success",
                                                Text.literal(blockName).styled { style: Style ->
                                                    style.withColor(Formatting.AQUA).withHoverEvent(
                                                        HoverEvent(
                                                            HoverEvent.Action.SHOW_ITEM,
                                                            HoverEvent.ItemStackContent(itemStack)
                                                        )
                                                    )
                                                }
                                            )
                                        }, false)
                                    }
                                }
                                Config.save()
                                Command.SINGLE_SUCCESS
                            })
                    )
                )
            )
        }
    }
}
