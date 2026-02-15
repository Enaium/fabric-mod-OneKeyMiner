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

import cn.enaium.onekeyminer.Config.model
import cn.enaium.onekeyminer.ROOT
import cn.enaium.onekeyminer.common.Tool
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.HoverEvent
import net.minecraft.text.LiteralText
import net.minecraft.text.MutableText
import net.minecraft.text.Style
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

/**
 * @author Enaium
 */
fun listCommand(dispatcher: CommandDispatcher<ServerCommandSource>) {
    for (tool in Tool.entries) {
        dispatcher.register(
            ROOT.then(
                CommandManager.literal(tool.name)
                    .then(CommandManager.literal("list").executes { context: CommandContext<ServerCommandSource> ->
                        val list = when (tool) {
                            Tool.AXE -> model.axe
                            Tool.HOE -> model.hoe
                            Tool.PICKAXE -> model.pickaxe
                            Tool.SHOVEL -> model.shovel
                            Tool.SHEARS -> model.shears
                            Tool.ANY -> model.any
                        }
                        var previous: MutableText? = null
                        for (name in list) {
                            val item = LiteralText(name)
                                .styled { style: Style ->
                                    style.withHoverEvent(
                                        HoverEvent(
                                            HoverEvent.Action.SHOW_ITEM,
                                            HoverEvent.ItemStackContent(
                                                Registry.BLOCK[Identifier(name)].asItem().defaultStack
                                            )
                                        )
                                    )
                                }
                            if (previous == null) {
                                previous = item
                            } else {
                                previous.append(LiteralText(", "))
                                previous.append(item)
                            }
                        }
                        if (previous != null) {
                            context.source.sendFeedback(previous, false)
                        }
                        Command.SINGLE_SUCCESS
                    })
            )
        )
    }
}