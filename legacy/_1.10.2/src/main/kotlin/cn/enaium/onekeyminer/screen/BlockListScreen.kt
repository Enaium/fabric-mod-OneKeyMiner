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

class BlockListScreen(private val list: MutableList<String>) : Screen() {
    private var entryListWidget: ListWidget<BlockListWidget.Entry>? = null
    private var removeButton: ButtonWidget? = null

    override fun init() {
        entryListWidget = ListWidget(client, width, height, 50, height - 50, 24)
        list.forEach { it: String ->
            entryListWidget!!.addEntry(
                BlockListWidget.Entry(
                    it
                )
            )
        }
        val addButton =
            ButtonWidget(0, width / 2 - 100, 15, 200, 20, TranslatableText("button.add").asFormattedString())
        removeButton = ButtonWidget(
            1,
            width / 2 - 100,
            height - 35,
            200,
            20,
            TranslatableText("button.remove").asFormattedString()
        )
        addButton(addButton)
        addButton(removeButton)
        super.init()
    }

    override fun handleMouse() {
        entryListWidget!!.handleMouse()
        super.handleMouse()
    }

    override fun buttonClicked(button: ButtonWidget) {
        if (button.id == 0) {
            MinecraftClient.getInstance().setScreen(BlockListAllScreen(this, list))
        } else if (button.id == 1) {
            if (entryListWidget!!.selected != null) {
                list.remove(entryListWidget!!.selected!!.name)
                Config.save()
                entryListWidget!!.removeEntry(entryListWidget!!.selected!!)
            }
        }
        super.buttonClicked(button)
    }

    override fun render(mouseX: Int, mouseY: Int, tickDelta: Float) {
        renderBackground()
        entryListWidget!!.render(mouseX, mouseY, tickDelta)
        removeButton!!.active = entryListWidget!!.selected != null
        super.render(mouseX, mouseY, tickDelta)
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {
        entryListWidget!!.mouseClicked(mouseX, mouseY, button)
        super.mouseClicked(mouseX, mouseY, button)
    }

    override fun mouseReleased(mouseX: Int, mouseY: Int, button: Int) {
        entryListWidget!!.mouseReleased(mouseX, mouseY, button)
        super.mouseReleased(mouseX, mouseY, button)
    }
}