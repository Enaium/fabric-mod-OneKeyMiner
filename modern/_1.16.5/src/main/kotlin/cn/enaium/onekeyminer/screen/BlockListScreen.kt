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
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.text.LiteralText
import net.minecraft.text.TranslatableText


/**
 * @author Enaium
 */
class BlockListScreen(private val list: MutableList<String>) : Screen(LiteralText("")) {
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
        val addButton = ButtonWidget(
            width / 2 - 100, 15, 200, 20, TranslatableText("button.add")
        ) { MinecraftClient.getInstance().openScreen(BlockListAllScreen(this, list)) }
        removeButton = ButtonWidget(
            width / 2 - 100, height - 35, 200, 20, TranslatableText("button.remove")
        ) {
            if (entryListWidget!!.selected != null) {
                list.remove(entryListWidget!!.selected!!.name)
                save()
                entryListWidget!!.removeEntry(entryListWidget!!.selected)
            }
        }
        addChild<ListWidget<BlockListWidget.Entry>>(entryListWidget)
        addButton(addButton)
        addButton(removeButton)
        super.init()
    }

    override fun render(matrices: MatrixStack, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(matrices)
        removeButton!!.active = entryListWidget!!.selected != null
        entryListWidget!!.render(matrices, mouseX, mouseY.toFloat().toInt(), delta)
        super.render(matrices, mouseX, mouseY.toFloat().toInt(), delta)
    }
}
