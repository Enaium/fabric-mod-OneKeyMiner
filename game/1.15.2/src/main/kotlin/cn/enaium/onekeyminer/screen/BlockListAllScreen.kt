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

import cn.enaium.onekeyminer.Config.save
import cn.enaium.onekeyminer.utility.getName
import net.minecraft.block.Block
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.gui.widget.TextFieldWidget
import net.minecraft.text.LiteralText
import net.minecraft.text.TranslatableText
import net.minecraft.util.registry.Registry
import java.util.stream.Collectors


/**
 * @author Enaium
 */
class BlockListAllScreen(private val parent: Screen, private val list: MutableList<String>) : Screen(LiteralText("")) {
    private var entryListWidget: ListWidget<BlockListWidget.Entry>? = null
    private var textFieldWidget: TextFieldWidget? = null
    private var addButton: ButtonWidget? = null
    public override fun init() {
        entryListWidget = ListWidget(minecraft, width, height, 50, height - 50, 24)
        textFieldWidget =
            TextFieldWidget(MinecraftClient.getInstance().textRenderer, width / 2 - 100, 15, 200, 20, "")
        addButton = ButtonWidget(
            width / 2 - 100, height - 35, 200, 20, TranslatableText("button.add").asString()
        ) {
            val selectedOrNull = entryListWidget!!.selected
            if (selectedOrNull != null) {
                list.add(selectedOrNull.name)
                save()
                MinecraftClient.getInstance().openScreen(parent)
            }
        }
        get().forEach { entry: BlockListWidget.Entry -> entryListWidget!!.addEntry(entry) }
        textFieldWidget!!.setChangedListener {
            entryListWidget!!.replaceEntries(get())
            entryListWidget!!.setSelected(null)
        }
        children.add(entryListWidget)
        children.add(textFieldWidget)
        addButton(addButton)
        super.init()
    }

    fun get(): List<BlockListWidget.Entry> {
        return Registry.BLOCK.stream().filter { it: Block ->
            if (textFieldWidget!!.text != "") {
                return@filter it.asItem().toString()
                    .contains(textFieldWidget!!.text) || TranslatableText(it.asItem().translationKey).string.contains(
                    textFieldWidget!!.text
                )
            } else {
                return@filter true
            }
        }
            .map { it: Block -> BlockListWidget.Entry(it.dropTableId.getName()) }
            .collect(Collectors.toList())
    }

    override fun render(mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground()
        addButton!!.active = entryListWidget!!.selected != null
        entryListWidget!!.render(mouseX, mouseY, delta)
        textFieldWidget!!.render(mouseX, mouseY, delta)
        super.render(mouseX, mouseY, delta)
    }
}