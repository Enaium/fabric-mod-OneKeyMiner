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

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.Identifier
import kotlin.jvm.optionals.getOrNull

/**
 * @author Enaium
 */
class BlockListWidget {
    class Entry(val name: String) : ListWidget.Entry<Entry>() {
        override fun renderContent(graphics: GuiGraphics, mouseX: Int, mouseY: Int, hovered: Boolean, a: Float) {
            val font = Minecraft.getInstance().font
            val itemStack = BuiltInRegistries.ITEM[Identifier.parse(name)].getOrNull()?.value()?.defaultInstance
            if (itemStack?.isEmpty == false) {
                graphics.renderItem(itemStack, x, y)
                graphics.drawString(
                    font,
                    itemStack.itemName,
                    (x + contentWidth - font.width(itemStack.itemName)),
                    (y + font.lineHeight),
                    -1, false
                )
            }
            graphics.drawString(
                font,
                name,
                (x + contentWidth - font.width(name)),
                y,
                -1, false
            )
            super.renderContent(graphics, mouseX, mouseY, hovered, a)
        }
    }
}
