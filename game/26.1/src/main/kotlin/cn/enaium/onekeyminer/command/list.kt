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
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.HoverEvent
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.Style
import net.minecraft.resources.Identifier
import net.minecraft.world.item.ItemStackTemplate

/**
 * @author Enaium
 */
fun listCommand(dispatcher: CommandDispatcher<CommandSourceStack>) {
    for (tool in Tool.entries) {
        dispatcher.register(
            ROOT.then(
                Commands.literal(tool.name)
                    .then(Commands.literal("list").executes { context: CommandContext<CommandSourceStack> ->
                        val list = when (tool) {
                            Tool.AXE -> model.axe
                            Tool.HOE -> model.hoe
                            Tool.PICKAXE -> model.pickaxe
                            Tool.SHOVEL -> model.shovel
                            Tool.SHEARS -> model.shears
                            Tool.ANY -> model.any
                        }
                        var previous: MutableComponent? = null
                        for (i in list.indices) {
                            val item = Component.literal(list[i])
                                .withStyle { style: Style ->
                                    style.withHoverEvent(
                                        HoverEvent.ShowItem(
                                            ItemStackTemplate.fromNonEmptyStack(
                                                BuiltInRegistries.BLOCK[Identifier.parse(list[i])].get().value()
                                                    .asItem().defaultInstance
                                            )
                                        )
                                    )
                                }
                            if (previous == null) {
                                previous = item
                            } else {
                                previous.append(Component.literal(", "))
                                previous.append(item)
                            }
                        }
                        if (previous != null) {
                            context.source.sendSystemMessage(previous)
                        }
                        Command.SINGLE_SUCCESS
                    })
            )
        )
    }
}
