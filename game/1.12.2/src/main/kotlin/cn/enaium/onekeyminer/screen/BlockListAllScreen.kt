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
import cn.enaium.onekeyminer.utility.getName
import net.minecraft.block.Block
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.gui.widget.PagedEntryListWidget
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.text.TranslatableText

class BlockListAllScreen(private val parent: Screen, private val list: MutableList<String>) : Screen() {
    private var entryListWidget: ListWidget<BlockListWidget.Entry>? = null
    private var textFieldWidget: TextFieldWidget? = null
    private var addButton: ButtonWidget? = null

    override fun init() {
        entryListWidget = ListWidget(client, width, height, 50, height - 50, 24)
        textFieldWidget =
            TextFieldWidget(0, MinecraftClient.getInstance().textRenderer, width / 2 - 100, 15, 200, 20)
        textFieldWidget!!.setListener(object : PagedEntryListWidget.Listener {
            override fun setBooleanValue(id: Int, value: Boolean) {

            }

            override fun setFloatValue(id: Int, value: Float) {

            }

            override fun setStringValue(id: Int, text: String) {
                entryListWidget!!.replaceEntries(get())
                entryListWidget!!.selected = null
            }
        })
        addButton =
            ButtonWidget(1, width / 2 - 100, height - 35, 200, 20, TranslatableText("button.add").asFormattedString())
        get().forEach { entry: BlockListWidget.Entry -> entryListWidget!!.addEntry(entry) }

        addButton(addButton)
        super.init()
    }

    override fun handleMouse() {
        entryListWidget!!.handleMouse()
        super.handleMouse()
    }

    fun get(): List<BlockListWidget.Entry> {
        val temp: MutableList<BlockListWidget.Entry> = ArrayList()
        for (block in Block.REGISTRY) {
            if (textFieldWidget!!.text != "") {
                if (Block.REGISTRY.getIdentifier(block).getName().contains(textFieldWidget!!.text) || TranslatableText(
                        block.translationKey
                    ).asFormattedString().contains(textFieldWidget!!.text)
                ) {
                    temp.add(BlockListWidget.Entry(Block.REGISTRY.getIdentifier(block).getName()))
                }
            } else {
                temp.add(BlockListWidget.Entry(Block.REGISTRY.getIdentifier(block).getName()))
            }
        }
        return temp
    }

    override fun render(mouseX: Int, mouseY: Int, tickDelta: Float) {
        renderBackground()
        addButton!!.active = entryListWidget!!.selected != null
        entryListWidget!!.render(mouseX, mouseY, tickDelta)
        textFieldWidget!!.render()
        super.render(mouseX, mouseY, tickDelta)
    }

    override fun mouseClicked(mouseX: Int, mouseY: Int, button: Int) {
        entryListWidget!!.mouseClicked(mouseX, mouseY, button)
        textFieldWidget!!.method_920(mouseX, mouseY, button)
        super.mouseClicked(mouseX, mouseY, button)
    }

    override fun keyPressed(id: Char, code: Int) {
        textFieldWidget!!.keyPressed(id, code)
        super.keyPressed(id, code)
    }

    override fun buttonClicked(button: ButtonWidget) {
        if (button.id == 1) {
            val selectedOrNull = entryListWidget!!.selected
            if (selectedOrNull != null) {
                list.add(selectedOrNull.name)
                Config.save()
                MinecraftClient.getInstance().setScreen(parent)
            }
        }
        super.buttonClicked(button)
    }
}