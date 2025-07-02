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
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text

/**
 * @author Enaium
 */
fun limitCommand(dispatcher: CommandDispatcher<ServerCommandSource>) {
    dispatcher.register(
        ROOT.then(
            CommandManager.literal("limit").executes { context: CommandContext<ServerCommandSource> ->
                context.source.sendFeedback(
                    { Text.translatable("command.limit.get", model.limit) }, false
                )
                Command.SINGLE_SUCCESS
            }.then(
                CommandManager.argument("limit", IntegerArgumentType.integer())
                    .executes { context: CommandContext<ServerCommandSource> ->
                        val limit = IntegerArgumentType.getInteger(context, "limit")
                        if (limit <= 0) {
                            context.source.sendError(Text.translatable("command.limit.error"))
                            return@executes Command.SINGLE_SUCCESS
                        }
                        model.limit = limit
                        context.source.sendFeedback({ Text.translatable("command.limit.success", limit) }, false)
                        Command.SINGLE_SUCCESS
                    })
        )
    )
}