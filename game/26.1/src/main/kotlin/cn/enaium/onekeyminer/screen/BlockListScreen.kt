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
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.screens.Screen
import net.minecraft.network.chat.Component

/**
 * @author Enaium
 */
class BlockListScreen(private val list: MutableList<String>) : Screen(Component.literal("")) {
    private var entryListWidget: ListWidget<BlockListWidget.Entry>? = null
    private var removeButton: Button? = null
    override fun init() {
        entryListWidget = ListWidget(minecraft, width, height - 100, 50, 24)
        list.forEach { it: String ->
            entryListWidget!!.addEntry(
                BlockListWidget.Entry(
                    it
                )
            )
        }
        val addButton = Button.builder(Component.translatable("button.add")) { e: Button ->
            minecraft.setScreen(
                BlockListAllScreen(this, list)
            )
        }
            .bounds(width / 2 - 100, 15, 200, 20).build()
        removeButton = Button.builder(Component.translatable("button.remove")) { e: Button ->
            if (entryListWidget?.selected != null) {
                list.remove(entryListWidget?.selected?.name)
                Config.save()
                entryListWidget?.removeEntry(entryListWidget?.selected!!)
            }
        }.bounds(width / 2 - 100, height - 35, 200, 20).build()
        addRenderableWidget(entryListWidget!!)
        addRenderableWidget(addButton)
        addRenderableWidget(removeButton!!)
        super.init()
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, a: Float) {
        removeButton?.active = entryListWidget?.selected != null
        super.render(graphics, mouseX, mouseY, a)
    }
}
