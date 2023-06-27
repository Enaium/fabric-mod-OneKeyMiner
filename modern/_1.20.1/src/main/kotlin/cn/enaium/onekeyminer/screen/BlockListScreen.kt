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
import net.minecraft.client.gui.DrawContext
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.gui.widget.ButtonWidget
import net.minecraft.text.Text
import java.util.function.Consumer

/**
 * @author Enaium
 */
class BlockListScreen(private val list: MutableList<String>) : Screen(Text.literal("")) {
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
        val addButton = ButtonWidget.builder(Text.translatable("button.add")) { e: ButtonWidget ->
            MinecraftClient.getInstance().setScreen(
                BlockListAllScreen(this, list)
            )
        }
            .dimensions(width / 2 - 100, 15, 200, 20).build()
        removeButton = ButtonWidget.builder(Text.translatable("button.remove")) { e: ButtonWidget ->
            if (entryListWidget!!.selectedOrNull != null) {
                list.remove(entryListWidget!!.selectedOrNull!!.name)
                save()
                entryListWidget!!.removeEntry(entryListWidget!!.selectedOrNull)
            }
        }.dimensions(width / 2 - 100, height - 35, 200, 20).build()
        addDrawableChild<ListWidget<BlockListWidget.Entry>>(entryListWidget)
        addDrawableChild(addButton)
        addDrawableChild(removeButton)
        super.init()
    }

    override fun render(context: DrawContext, mouseX: Int, mouseY: Int, delta: Float) {
        renderBackground(context)
        removeButton!!.active = entryListWidget!!.selectedOrNull != null
        super.render(context, mouseX, mouseY, delta)
    }
}
