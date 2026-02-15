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

import cn.enaium.onekeyminer.Config.model
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.text.Text

/**
 * @author Enaium
 */
class ToolSelectScreen : Screen(Text.literal("")) {
    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(context, mouseX, mouseY, delta)
        addDrawableChild(ButtonWidget.builder(Text.translatable("button.axe")) {
            MinecraftClient.getInstance().setScreen(
                BlockListScreen(model.axe)
            )
        }
            .dimensions(0, 0, 200, 20).build())
        addDrawableChild(ButtonWidget.builder(Text.translatable("button.hoe")) {
            MinecraftClient.getInstance().setScreen(
                BlockListScreen(model.hoe)
            )
        }
            .dimensions(0, 30, 200, 20).build())
        addDrawableChild(ButtonWidget.builder(Text.translatable("button.pickaxe")) {
            MinecraftClient.getInstance().setScreen(
                BlockListScreen(model.pickaxe)
            )
        }
            .dimensions(0, 60, 200, 20).build())
        addDrawableChild(ButtonWidget.builder(Text.translatable("button.shovel")) {
            MinecraftClient.getInstance().setScreen(
                BlockListScreen(model.shovel)
            )
        }
            .dimensions(0, 90, 200, 20).build())
        addDrawableChild(ButtonWidget.builder(Text.translatable("button.shears")) {
            MinecraftClient.getInstance().setScreen(
                BlockListScreen(model.shears)
            )
        }
            .dimensions(0, 120, 200, 20).build())
        addDrawableChild(ButtonWidget.builder(Text.translatable("button.any")) {
            MinecraftClient.getInstance().setScreen(
                BlockListScreen(model.any)
            )
        }
            .dimensions(0, 150, 200, 20).build())
        super.render(context, mouseX, mouseY, delta)
    }
}