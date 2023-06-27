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
import net.minecraft.command.arguments.BlockStateArgument
import net.minecraft.command.arguments.BlockStateArgumentType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.HoverEvent
import net.minecraft.text.LiteralText
import net.minecraft.text.TranslatableText
import net.minecraft.util.Formatting


/**
 * @author Enaium
 */
fun actionCommand(dispatcher: CommandDispatcher<ServerCommandSource>) {
    for (tool in Tool.values()) {
        for (action in Action.values()) {
            dispatcher.register(
                ROOT.then(
                    CommandManager.literal(tool.name).then(
                        CommandManager.literal(action.name).then(
                            CommandManager.argument(
                                "block",
                                BlockStateArgumentType.blockState()
                            ).executes { context: CommandContext<ServerCommandSource> ->
                                val block = context.getArgument("block", BlockStateArgument::class.java)
                                val blockName: String = getName(block.blockState.block.dropTableId)
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
                                        context.source.sendFeedback(
                                            TranslatableText(
                                                "command.action.add.success",
                                                LiteralText(blockName).styled { style ->
                                                    style.setColor(Formatting.AQUA).setHoverEvent(
                                                        HoverEvent(
                                                            HoverEvent.Action.SHOW_TEXT,
                                                            TranslatableText(block.blockState.block.translationKey)
                                                        )
                                                    )
                                                }), false
                                        )
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
                                        context.source.sendFeedback(
                                            TranslatableText(
                                                "command.action.remove.success",
                                                LiteralText(blockName).styled { style ->
                                                    style.setColor(Formatting.AQUA).setHoverEvent(
                                                        HoverEvent(
                                                            HoverEvent.Action.SHOW_TEXT,
                                                            TranslatableText(block.blockState.block.translationKey)
                                                        )
                                                    )
                                                }), false
                                        )
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
