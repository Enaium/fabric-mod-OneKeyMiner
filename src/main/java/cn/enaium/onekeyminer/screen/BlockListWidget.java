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
package cn.enaium.onekeyminer.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * @author Enaium
 */
public class BlockListWidget {
    public static class Entry extends ListWidget.Entry<Entry> {

        public final String name;

        public Entry(String name) {
            this.name = name;
        }

        @Override
        public void render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            var textRenderer = MinecraftClient.getInstance().textRenderer;
            var itemStack = new ItemStack(Registries.ITEM.get(new Identifier(name)));
            if (!itemStack.isEmpty()) {
                MinecraftClient.getInstance().getItemRenderer().renderGuiItemIcon(matrices, itemStack, x, y);
                textRenderer.draw(matrices, Text.translatable(itemStack.getTranslationKey()).getString(),
                        x + entryWidth - textRenderer.getWidth(Text.translatable(itemStack.getTranslationKey()).getString()),
                        y + textRenderer.fontHeight, 0xFFFFFFFF);
            }
            textRenderer.draw(matrices, name, x + entryWidth - textRenderer.getWidth(name), y, 0xFFFFFFFF);
            super.render(matrices, index, y, x, entryWidth, entryHeight, mouseX, mouseY, hovered, tickDelta);
        }
    }
}
