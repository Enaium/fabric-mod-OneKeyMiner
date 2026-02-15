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

package cn.enaium.onekeyminer

import cn.enaium.onekeyminer.command.*
import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback
import net.minecraft.server.command.ServerCommandSource

/**
 * @author Enaium
 */
object Commands {
    @JvmStatic
    fun initializer() {
        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher: CommandDispatcher<ServerCommandSource>, _: Boolean ->
            actionCommand(dispatcher)
            interactCommand(dispatcher)
            limitCommand(dispatcher)
            listCommand(dispatcher)
            reloadCommand(dispatcher)
        })
    }

    @JvmStatic
    fun client() {
        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher: CommandDispatcher<ServerCommandSource>, _: Boolean ->
            screenCommand(dispatcher)
            hotkeyCommand(dispatcher)
        })
    }
}