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
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

/**
 * @author Enaium
 */
class ToolSelectScreen : Screen(Component.literal("")) {
    override fun init() {
        addRenderableWidget(Button.builder(Component.translatable("button.axe")) {
            minecraft.setScreen(
                BlockListScreen(model.axe)
            )
        }
            .bounds(0, 0, 200, 20).build())
        addRenderableWidget(Button.builder(Component.translatable("button.hoe")) {
            minecraft.setScreen(
                BlockListScreen(model.hoe)
            )
        }
            .bounds(0, 30, 200, 20).build())
        addRenderableWidget(Button.builder(Component.translatable("button.pickaxe")) {
            minecraft.setScreen(
                BlockListScreen(model.pickaxe)
            )
        }
            .bounds(0, 60, 200, 20).build())
        addRenderableWidget(Button.builder(Component.translatable("button.shovel")) {
            minecraft.setScreen(
                BlockListScreen(model.shovel)
            )
        }
            .bounds(0, 90, 200, 20).build())
        addRenderableWidget(Button.builder(Component.translatable("button.shears")) {
            minecraft.setScreen(
                BlockListScreen(model.shears)
            )
        }
            .bounds(0, 120, 200, 20).build())
        addRenderableWidget(Button.builder(Component.translatable("button.any")) {
            minecraft.setScreen(
                BlockListScreen(model.any)
            )
        }
            .bounds(0, 150, 200, 20).build())
        super.init()
    }
}
