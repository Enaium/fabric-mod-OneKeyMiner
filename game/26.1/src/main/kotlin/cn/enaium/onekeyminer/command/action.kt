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
import cn.enaium.onekeyminer.common.Action
import cn.enaium.onekeyminer.common.Tool
import cn.enaium.onekeyminer.event.ServerCommandCallbacks
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.ChatFormatting
import net.minecraft.commands.CommandBuildContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.commands.arguments.blocks.BlockStateArgument
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.HoverEvent
import net.minecraft.network.chat.Style
import net.minecraft.world.item.ItemStackTemplate

/**
 * @author Enaium
 */
fun actionCommand(dispatcher: CommandDispatcher<CommandSourceStack>, buildContext: CommandBuildContext) {
    for (tool in Tool.entries) {
        for (action in Action.entries) {
            dispatcher.register(
                ROOT.then(
                    Commands.literal(tool.name).then(
                        Commands.literal(action.name).then(
                            Commands.argument(
                                "block",
                                BlockStateArgument.block(buildContext)
                            ).executes { context: CommandContext<CommandSourceStack> ->
                                val block = BlockStateArgument.getBlock(context, "block")
                                val itemStack = block.state.block.asItem().defaultInstance
                                val blockName: String = BuiltInRegistries.BLOCK.getKey(block.state.block).toString()
                                when (action) {
                                    Action.ADD -> {
                                        ServerCommandCallbacks.ActionCallback.EVENT.invoker.execute(
                                            tool,
                                            action,
                                            blockName
                                        )
                                        context.source.sendSystemMessage(
                                            Component.translatable(
                                                "command.action.add.success",
                                                Component.literal(blockName).withStyle { style: Style ->
                                                    style.withColor(ChatFormatting.AQUA).withHoverEvent(
                                                        HoverEvent.ShowItem(
                                                            ItemStackTemplate.fromNonEmptyStack(itemStack)
                                                        )
                                                    )
                                                }
                                            )
                                        )
                                    }

                                    Action.REMOVE -> {
                                        ServerCommandCallbacks.ActionCallback.EVENT.invoker.execute(
                                            tool,
                                            action,
                                            blockName
                                        )
                                        context.source.sendSystemMessage(
                                            Component.translatable(
                                                "command.action.remove.success",
                                                Component.literal(blockName).withStyle { style: Style ->
                                                    style.withColor(ChatFormatting.AQUA).withHoverEvent(
                                                        HoverEvent.ShowItem(
                                                            ItemStackTemplate.fromNonEmptyStack(itemStack)
                                                        )
                                                    )
                                                }
                                            )
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
