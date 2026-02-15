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

import cn.enaium.onekeyminer.event.ServerCommandCallbacks
import cn.enaium.onekeyminer.event.ServerPlayerCallbacks
import cn.enaium.onekeyminer.event.impl.*

/**
 * @author Enaium
 */
object OneKeyMiner {
    @JvmStatic
    fun initializer() {
        ServerCommandCallbacks.ReloadCallback.EVENT.register(ReloadCallbackImpl())
        ServerCommandCallbacks.LimitCallback.EVENT.register(LimitCallbackImpl())
        ServerCommandCallbacks.InteractCallback.EVENT.register(InteractCallbackImpl())
        ServerCommandCallbacks.ActionCallback.EVENT.register(ActionCallbackImpl())

        Config.load()
        Runtime.getRuntime().addShutdownHook(Thread(Config::save))
    }

    @JvmStatic
    fun client() {
        ServerCommandCallbacks.HotkeyCallback.EVENT.register(HotkeyCallbackImpl())
        ServerPlayerCallbacks.FinishMiningCallback.EVENT.register(FinishMiningCallbackClientImpl())
        ServerPlayerCallbacks.UseOnBlockCallback.EVENT.register(UseOnBlockCallbackClientImpl())
    }

    @JvmStatic
    fun server() {
        ServerPlayerCallbacks.FinishMiningCallback.EVENT.register(FinishMiningCallbackServerImpl())
        ServerPlayerCallbacks.UseOnBlockCallback.EVENT.register(UseOnBlockCallbackServerImpl())
    }
}