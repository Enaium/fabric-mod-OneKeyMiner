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
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.components.Button
import net.minecraft.client.gui.components.EditBox
import net.minecraft.client.gui.screens.Screen
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.chat.Component
import net.minecraft.resources.Identifier
import net.minecraft.world.level.block.Block
import java.util.stream.Collectors

/**
 * @author Enaium
 */
class BlockListAllScreen(private val parent: Screen, private val list: MutableList<String>) :
    Screen(Component.literal("")) {
    private var entryListWidget: ListWidget<BlockListWidget.Entry>? = null
    private var editBox: EditBox? = null
    private var addButton: Button? = null
    public override fun init() {
        entryListWidget = ListWidget(minecraft, width, height - 100, 50, 24)
        editBox = EditBox(minecraft.font, width / 2 - 100, 15, 200, 20, Component.literal(""))
        addButton = Button.builder(Component.translatable("button.add")) { e: Button? ->
            val selectedOrNull = entryListWidget?.selected
            if (selectedOrNull != null) {
                list.add(selectedOrNull.name)
                save()
                minecraft.setScreen(parent)
            }
        }.bounds(width / 2 - 100, height - 35, 200, 20).build()
        get().forEach { entry: BlockListWidget.Entry -> entryListWidget!!.addEntry(entry) }
        editBox?.setResponder {
            entryListWidget?.replaceEntries(get())
            entryListWidget?.selected = null
        }
        addRenderableWidget(entryListWidget!!)
        addRenderableWidget(editBox!!)
        addRenderableWidget(addButton!!)
        super.init()
    }

    fun get(): List<BlockListWidget.Entry> {
        return BuiltInRegistries.BLOCK.stream().filter { it: Block ->
            if (editBox?.value != "") {
                return@filter it.asItem().toString()
                    .contains(editBox!!.value) || Component.translatable(
                    Identifier.parse(
                        it.asItem().toString()
                    ).toLanguageKey()
                ).string.contains(
                    editBox!!.value
                )
            } else {
                return@filter true
            }
        }
            .map { it: Block -> BlockListWidget.Entry(BuiltInRegistries.BLOCK.getKey(it).toString()) }
            .collect(Collectors.toList())
    }

    override fun render(graphics: GuiGraphics, mouseX: Int, mouseY: Int, a: Float) {
        addButton?.active = entryListWidget?.selected != null
        super.render(graphics, mouseX, mouseY, a)
    }
}
