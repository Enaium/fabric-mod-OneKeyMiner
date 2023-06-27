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

import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.widget.EntryListWidget

/**
 * @author Enaium
 */
class ListWidget<T : ListWidget.Entry<T>>(
    client: MinecraftClient,
    width: Int,
    height: Int,
    top: Int,
    bottom: Int,
    itemHeight: Int
) : EntryListWidget(client, width, height, top, bottom, itemHeight) {
    private val entries: MutableList<T> = ArrayList()
    var selected: T? = null

    override fun getEntryCount(): Int {
        return entries.size
    }

    override fun getEntry(index: Int): EntryListWidget.Entry {
        return entries[index]
    }

    fun addEntry(entry: T) {
        entries.add(entry)
    }

    fun removeEntry(entry: T) {
        entries.remove(entry)
        selected = null
    }

    fun replaceEntries(newEntries: List<T>) {
        entries.clear()
        entries.addAll(newEntries)
    }

    override fun selectEntry(index: Int, doubleClick: Boolean, lastMouseX: Int, lastMouseY: Int) {
        selected = entries[index]
        super.selectEntry(index, doubleClick, lastMouseX, lastMouseY)
    }

    override fun isEntrySelected(index: Int): Boolean {
        return entries[index] == selected
    }

    open class Entry<E : EntryListWidget.Entry> : EntryListWidget.Entry {
        private var hovered = false
        override fun method_9473(i: Int, j: Int, k: Int, f: Float) {}
        override fun method_6700(
            index: Int,
            y: Int,
            x: Int,
            width: Int,
            height: Int,
            mouseX: Int,
            mouseY: Int,
            hovering: Boolean,
            delta: Float
        ) {
            hovered = hovering
        }

        override fun mouseClicked(index: Int, mouseX: Int, mouseY: Int, button: Int, x: Int, y: Int): Boolean {
            return false
        }

        override fun mouseReleased(index: Int, mouseX: Int, mouseY: Int, button: Int, x: Int, y: Int) {}
    }
}
