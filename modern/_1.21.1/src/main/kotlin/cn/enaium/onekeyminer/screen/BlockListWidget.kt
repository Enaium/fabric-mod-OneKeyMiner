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
import net.minecraft.client.gui.DrawContext
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.text.Text
import net.minecraft.util.Identifier

/**
 * @author Enaium
 */
class BlockListWidget {
    class Entry(val name: String) : ListWidget.Entry<Entry>() {
        override fun render(
            context: DrawContext,
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
            val itemStack = ItemStack(Registries.ITEM[Identifier.tryParse(name)])
            if (!itemStack.isEmpty) {
                context.drawItem(itemStack, x, y)
                context.drawText(
                    textRenderer,
                    Text.translatable(itemStack.translationKey),
                    (x + entryWidth - textRenderer.getWidth(Text.translatable(itemStack.translationKey).string)),
                    (y + textRenderer.fontHeight),
                    0xFFFFFF, false
                )
            }
            context.drawText(
                textRenderer,
                name,
                (x + entryWidth - textRenderer.getWidth(name)),
                y,
                0xFFFFFF, false
            )
            super.render(context, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta)
        }
    }
}
