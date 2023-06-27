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
import net.minecraft.text.LiteralText
import net.minecraft.text.TranslatableText


/**
 * @author Enaium
 */
class ToolSelectScreen : Screen(LiteralText("")) {
    override fun render(mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground()

        addButton(
            ButtonWidget(
                0, 0, 200, 20, TranslatableText("button.axe").asString()
            ) { MinecraftClient.getInstance().openScreen(BlockListScreen(Config.model.axe)) }
        )

        addButton(
            ButtonWidget(
                0, 30, 200, 20, TranslatableText("button.hoe").asString()
            ) { MinecraftClient.getInstance().openScreen(BlockListScreen(Config.model.hoe)) }
        )

        addButton(
            ButtonWidget(
                0, 60, 200, 20, TranslatableText("button.pickaxe").asString()
            ) { MinecraftClient.getInstance().openScreen(BlockListScreen(Config.model.pickaxe)) }
        )

        addButton(
            ButtonWidget(
                0, 90, 200, 20, TranslatableText("button.shovel").asString()
            ) { MinecraftClient.getInstance().openScreen(BlockListScreen(Config.model.shovel)) }
        )

        addButton(
            ButtonWidget(
                0, 120, 200, 20, TranslatableText("button.shears").asString()
            ) { MinecraftClient.getInstance().openScreen(BlockListScreen(Config.model.shears)) }
        )

        addButton(
            ButtonWidget(
                0, 150, 200, 20, TranslatableText("button.any").asString()
            ) { MinecraftClient.getInstance().openScreen(BlockListScreen(Config.model.any)) }
        )
        super.render(mouseX, mouseY.toFloat().toInt(), delta)
    }
}
