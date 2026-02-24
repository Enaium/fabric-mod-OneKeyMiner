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
import cn.enaium.onekeyminer.event.ServerCommandCallbacks
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.BoolArgumentType
import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands
import net.minecraft.network.chat.Component

/**
 * @author Enaium
 */
fun hotkeyCommand(dispatcher: CommandDispatcher<CommandSourceStack>) {
    dispatcher.register(
        ROOT.then(
            Commands.literal("hotkey").executes { context: CommandContext<CommandSourceStack> ->
                context.source.sendSystemMessage(
                    Component.translatable("command.hotkey.get", model.hotkey)
                )
                Command.SINGLE_SUCCESS
            }.then(
                Commands.argument("hotkey", BoolArgumentType.bool())
                    .executes { context: CommandContext<CommandSourceStack> ->
                        ServerCommandCallbacks.HotkeyCallback.EVENT.invoker.execute(
                            BoolArgumentType.getBool(
                                context,
                                "hotkey"
                            )
                        )
                        context.source.sendSystemMessage(
                            Component.translatable("command.hotkey.success", model.hotkey)
                        )
                        Command.SINGLE_SUCCESS
                    })
        )
    )
}
