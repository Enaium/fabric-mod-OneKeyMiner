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

package cn.enaium.onekeyminer

import cn.enaium.onekeyminer.callback.FinishMiningCallback
import cn.enaium.onekeyminer.callback.UseOnBlockCallback
import cn.enaium.onekeyminer.callback.impl.FinishMiningCallbackImpl
import cn.enaium.onekeyminer.callback.impl.UseOnBlockCallbackImpl
import cn.enaium.onekeyminer.command.*
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.server.command.ServerCommandSource
import org.lwjgl.glfw.GLFW
import org.slf4j.Logger
import org.slf4j.LoggerFactory


/**
 * @author Enaium
 */
fun initializer() {
    LOGGER.info("Hello OneKeyMiner world!")
    CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher: CommandDispatcher<ServerCommandSource>, registryAccess: CommandRegistryAccess, environment: CommandManager.RegistrationEnvironment ->
        actionCommand(dispatcher, registryAccess)
        interactCommand(dispatcher)
        limitCommand(dispatcher)
        listCommand(dispatcher)
        reloadCommand(dispatcher)
    })

    FinishMiningCallback.EVENT.register(FinishMiningCallbackImpl())
    UseOnBlockCallback.EVENT.register(UseOnBlockCallbackImpl())

    Config.load()
    Runtime.getRuntime().addShutdownHook(Thread(Config::save))
}

var active: KeyBinding? = null

fun client() {
    active = KeyBindingHelper.registerKeyBinding(
        KeyBinding(
            "key.${ID}.active",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_GRAVE_ACCENT,
            "category.${ID}.title"
        )
    )

    CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher: CommandDispatcher<ServerCommandSource>, registryAccess: CommandRegistryAccess, environment: CommandManager.RegistrationEnvironment ->
        screenCommand(dispatcher)
    })
}

const val ID = "onekeyminer"

val LOGGER: Logger = LoggerFactory.getLogger("OneKeyMiner")

val ROOT: LiteralArgumentBuilder<ServerCommandSource> = literal(ID).requires { source ->
    source.hasPermissionLevel(4)
}