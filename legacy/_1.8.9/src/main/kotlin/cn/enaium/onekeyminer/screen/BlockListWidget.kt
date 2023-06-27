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

import net.minecraft.block.Block
import net.minecraft.client.MinecraftClient
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.render.DiffuseLighting
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier


class BlockListWidget {
    class Entry(val name: String) : ListWidget.Entry<Entry>() {
        override fun updatePosition(index: Int, x: Int, y: Int) {

        }

        override fun render(
            index: Int,
            x: Int,
            y: Int,
            width: Int,
            height: Int,
            mouseX: Int,
            mouseY: Int,
            hovering: Boolean
        ) {
            val textRenderer: TextRenderer = MinecraftClient.getInstance().textRenderer
            val block = Block.REGISTRY.get(Identifier(name))
            val itemStack = Item.REGISTRY.get(Identifier(name))
            if (itemStack != null) {
                DiffuseLighting.enable()
                MinecraftClient.getInstance().itemRenderer.renderGuiItemModel(ItemStack(itemStack), x, y)
                DiffuseLighting.disable()
                textRenderer.draw(
                    TranslatableText(block.translatedName).asFormattedString(),
                    x + width - textRenderer.getStringWidth(TranslatableText(block.translatedName).asFormattedString()),
                    y + textRenderer.fontHeight,
                    0xFFFFFF
                )
            }
            textRenderer.draw(name, x + width - textRenderer.getStringWidth(name), y, 0xFFFFFF)
        }
    }
}