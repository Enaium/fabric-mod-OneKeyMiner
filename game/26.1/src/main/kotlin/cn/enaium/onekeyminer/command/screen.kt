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

import cn.enaium.onekeyminer.ROOT
import cn.enaium.onekeyminer.screen.ToolSelectScreen
import com.mojang.brigadier.Command
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.context.CommandContext
import net.minecraft.client.Minecraft
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands

/**
 * @author Enaium
 */
fun screenCommand(dispatcher: CommandDispatcher<CommandSourceStack>) {
    dispatcher.register(
        ROOT.then(
            Commands.literal("screen").executes { context: CommandContext<CommandSourceStack> ->
                if (context.source.player != null && Minecraft.getInstance().player != null) {
                    if (context.source.player!!
                            .uuid == Minecraft.getInstance().player!!.uuid
                    ) {
                        Minecraft.getInstance()
                            .execute { Minecraft.getInstance().setScreen(ToolSelectScreen()) }
                    }
                }
                Command.SINGLE_SUCCESS
            })
    )
}
