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

package cn.enaium.onekeyminer.screen

import cn.enaium.onekeyminer.Config
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.text.TranslatableText

class ToolSelectScreen : Screen() {
    override fun init() {
        buttons.add(ButtonWidget(0, 0, 0, 200, 20, TranslatableText("button.axe").asFormattedString()))
        buttons.add(ButtonWidget(1, 0, 30, 200, 20, TranslatableText("button.hoe").asFormattedString()))
        buttons.add(ButtonWidget(2, 0, 60, 200, 20, TranslatableText("button.pickaxe").asFormattedString()))
        buttons.add(ButtonWidget(3, 0, 90, 200, 20, TranslatableText("button.shovel").asFormattedString()))
        buttons.add(ButtonWidget(4, 0, 120, 200, 20, TranslatableText("button.shears").asFormattedString()))
        buttons.add(ButtonWidget(5, 0, 150, 200, 20, TranslatableText("button.any").asFormattedString()))
        super.init()
    }

    override fun render(mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground()
        super.render(mouseX, mouseY, delta)
    }

    override fun buttonClicked(button: ButtonWidget) {
        when (button.id) {
            0 -> {
                MinecraftClient.getInstance().setScreen(BlockListScreen(Config.model.axe))
            }

            1 -> {
                MinecraftClient.getInstance().setScreen(BlockListScreen(Config.model.hoe))
            }

            2 -> {
                MinecraftClient.getInstance().setScreen(BlockListScreen(Config.model.pickaxe))
            }

            3 -> {
                MinecraftClient.getInstance().setScreen(BlockListScreen(Config.model.shovel))
            }

            4 -> {
                MinecraftClient.getInstance().setScreen(BlockListScreen(Config.model.shears))
            }

            5 -> {
                MinecraftClient.getInstance().setScreen(BlockListScreen(Config.model.any))
            }
        }
        super.buttonClicked(button)
    }
}