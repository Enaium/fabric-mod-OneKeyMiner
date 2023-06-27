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
import net.minecraft.item.ItemStack
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry


/**
 * @author Enaium
 */
class BlockListWidget {
    class Entry(val name: String) : ListWidget.Entry<Entry>() {
        override fun render(
            index: Int,
            y: Int,
            x: Int,
            entryWidth: Int,
            entryHeight: Int,
            mouseX: Int,
            mouseY: Int,
            hovered: Boolean,
            tickDelta: Float
        ) {
            val textRenderer = MinecraftClient.getInstance().textRenderer
            val itemStack = ItemStack(Registry.ITEM[Identifier(name)])
            if (!itemStack.isEmpty) {
                MinecraftClient.getInstance().itemRenderer.renderGuiItemIcon(itemStack, x, y)
                textRenderer.draw(
                    TranslatableText(itemStack.translationKey).string,
                    (x + entryWidth - textRenderer.getStringWidth(TranslatableText(itemStack.translationKey).string)).toFloat(),
                    (y + textRenderer.fontHeight).toFloat(), 0xFFFFFF
                )
            }
            textRenderer.draw(
                name,
                (x + entryWidth - textRenderer.getStringWidth(name)).toFloat(),
                y.toFloat(),
                0xFFFFFF
            )
            super.render(index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta)
        }
    }
}
