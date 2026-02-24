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

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW

/**
 * @author Enaium
 */
object KeyBinds {
    val activeKeyBind = KeyBinding(
        "key.${ID}.active",
        InputUtil.Type.KEYSYM,
        GLFW.GLFW_KEY_GRAVE_ACCENT,
        "key.category.${ID}.title"
    )

    @JvmStatic
    fun client() {
        KeyBindingHelper.registerKeyBinding(activeKeyBind)
    }
}