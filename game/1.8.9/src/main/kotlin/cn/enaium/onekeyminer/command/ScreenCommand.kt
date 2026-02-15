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

import cn.enaium.onekeyminer.screen.ToolSelectScreen
import net.minecraft.client.MinecraftClient
import net.minecraft.command.AbstractCommand
import net.minecraft.command.CommandSource

class ScreenCommand : AbstractCommand() {
    override fun getCommandName(): String {
        return "onekeyminer-screen"
    }

    override fun getUsageTranslationKey(source: CommandSource?): String? {
        return null
    }

    override fun execute(
        commandSource: CommandSource,
        args: Array<out String>
    ) {
        if (commandSource.entity != null && MinecraftClient.getInstance().player != null) {
            if (commandSource.entity!!
                    .uuid == MinecraftClient.getInstance().player!!.uuid
            ) {
                MinecraftClient.getInstance()
                    .execute { MinecraftClient.getInstance().setScreen(ToolSelectScreen()) }
            }
        }
    }
}