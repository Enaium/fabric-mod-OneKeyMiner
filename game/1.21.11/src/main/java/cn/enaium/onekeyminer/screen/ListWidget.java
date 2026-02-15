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
import net.minecraft.client.gui.Click;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.EntryListWidget;

import java.util.Collection;

/**
 * @author Enaium
 */
public class ListWidget<T extends ListWidget.Entry<T>> extends EntryListWidget<T> {
    public ListWidget(MinecraftClient client, int width, int height, int y, int itemHeight) {
        super(client, width, height, y, itemHeight);
    }

    @Override
    public boolean mouseClicked(Click click, boolean doubled) {
        for (int i = 0; i < children().size(); i++) {
            if (children().get(i).hovered) {
                setSelected(children().get(i));
            }
        }
        return super.mouseClicked(click, doubled);
    }

    @Override
    protected void removeEntry(T entry) {
        super.removeEntry(entry);
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    @Override
    public int addEntry(T entry) {
        return super.addEntry(entry);
    }

    @Override
    public void replaceEntries(Collection<T> newEntries) {
        super.replaceEntries(newEntries);
    }

    public static class Entry<E extends EntryListWidget.Entry<E>> extends EntryListWidget.Entry<E> {
        public boolean hovered = false;

        @Override
        public void render(DrawContext context, int mouseX, int mouseY, boolean hovered, float deltaTicks) {
            this.hovered = hovered;
        }
    }
}
