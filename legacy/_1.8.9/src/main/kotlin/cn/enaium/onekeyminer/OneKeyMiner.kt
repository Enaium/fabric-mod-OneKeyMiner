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
import cn.enaium.onekeyminer.callback.impl.FinishMiningCallbackClientImpl
import cn.enaium.onekeyminer.callback.impl.FinishMiningCallbackServerImpl
import cn.enaium.onekeyminer.callback.impl.UseOnBlockCallbackClientImpl
import cn.enaium.onekeyminer.callback.impl.UseOnBlockCallbackServerImpl
import cn.enaium.onekeyminer.command.OneKeyMinerCommand
import cn.enaium.onekeyminer.command.ScreenCommand
import net.legacyfabric.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.legacyfabric.fabric.api.registry.CommandRegistry
import net.minecraft.client.option.KeyBinding

fun initializer() {
    println("Hello OneKeyMiner world!")
    CommandRegistry.INSTANCE.register(OneKeyMinerCommand())

    Config.load()
    Runtime.getRuntime().addShutdownHook(Thread(Config::save))
}

object Client {
    var active: KeyBinding? = null

    @JvmStatic
    fun client() {
        active = KeyBindingHelper.registerKeyBinding(
            KeyBinding(
                "key.${ID}.active",
                41,
                "category.${ID}.title"
            )
        )

        FinishMiningCallback.EVENT.register(FinishMiningCallbackClientImpl())
        UseOnBlockCallback.EVENT.register(UseOnBlockCallbackClientImpl())

        CommandRegistry.INSTANCE.register(ScreenCommand())
    }
}

object Server {
    @JvmStatic
    fun server() {
        FinishMiningCallback.EVENT.register(FinishMiningCallbackServerImpl())
        UseOnBlockCallback.EVENT.register(UseOnBlockCallbackServerImpl())
    }
}

const val ID = "onekeyminer"