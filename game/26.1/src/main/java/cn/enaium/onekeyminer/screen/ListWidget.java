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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jspecify.annotations.NonNull;

import java.util.Collection;

/**
 * @author Enaium
 */
public class ListWidget<T extends ListWidget.Entry<T>> extends ObjectSelectionList<T> {
    public ListWidget(Minecraft client, int width, int height, int y, int itemHeight) {
        super(client, width, height, y, itemHeight);
    }

    @Override
    public boolean mouseClicked(@NonNull MouseButtonEvent event, boolean doubleClick) {
        for (int i = 0; i < children().size(); i++) {
            if (children().get(i).hovered) {
                setSelected(children().get(i));
            }
        }
        return super.mouseClicked(event, doubleClick);
    }

    @Override
    protected void removeEntry(T entry) {
        super.removeEntry(entry);
    }

    @Override
    public int addEntry(T entry) {
        return super.addEntry(entry);
    }

    @Override
    public void replaceEntries(@NonNull Collection<T> newEntries) {
        super.replaceEntries(newEntries);
    }

    public static class Entry<E extends ObjectSelectionList.Entry<E>> extends ObjectSelectionList.Entry<E> {
        public boolean hovered = false;


        @Override
        public void renderContent(@NonNull GuiGraphics graphics, int mouseX, int mouseY, boolean hovered, float a) {
            this.hovered = hovered;
        }

        @Override
        public @NonNull Component getNarration() {
            return Component.empty();
        }
    }
}
