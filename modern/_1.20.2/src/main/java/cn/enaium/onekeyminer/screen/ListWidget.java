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
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.EntryListWidget;

import java.util.Collection;

/**
 * @author Enaium
 */
public class ListWidget<T extends ListWidget.Entry<T>> extends EntryListWidget<T> {
    public ListWidget(MinecraftClient client, int width, int height, int top, int bottom, int itemHeight) {
        super(client, width, height, top, bottom, itemHeight);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (int i = 0; i < children().size(); i++) {
            if (children().get(i).hovered) {
                setSelected(children().get(i));
            }
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected boolean removeEntry(T entry) {
        return super.removeEntry(entry);
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {

    }

    @Override
    public int addEntry(T entry) {
        return super.addEntry(entry);
    }

    @Override
    protected void replaceEntries(Collection<T> newEntries) {
        super.replaceEntries(newEntries);
    }

    public static class Entry<E extends EntryListWidget.Entry<E>> extends EntryListWidget.Entry<E> {
        public boolean hovered = false;

        @Override
        public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
            this.hovered = hovered;
        }
    }
}
