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

import cn.enaium.onekeyminer.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

import java.util.List;

/**
 * @author Enaium
 */
public class BlockListScreen extends Screen {

    private final List<String> list;
    private ListWidget<BlockListWidget.Entry> entryListWidget;
    private ButtonWidget removeButton;

    public BlockListScreen(List<String> list) {
        super(new LiteralText(""));
        this.list = list;
    }

    @Override
    protected void init() {
        entryListWidget = new ListWidget<>(client, width, height, 50, height - 50, 24);

        list.forEach(it -> entryListWidget.addEntry(new BlockListWidget.Entry(it)));

        ButtonWidget addButton = new ButtonWidget(width / 2 - 100, 15, 200, 20, new TranslatableText("button.add"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListAllScreen(this, list));
        });
        removeButton = new ButtonWidget(width / 2 - 100, height - 35, 200, 20, new TranslatableText("button.remove"), e -> {
            if (entryListWidget.getSelectedOrNull() != null) {
                list.remove(entryListWidget.getSelectedOrNull().name);
                Config.save();
                entryListWidget.removeEntry(entryListWidget.getSelectedOrNull());
            }
        });
        addDrawableChild(entryListWidget);
        addDrawableChild(addButton);
        addDrawableChild(removeButton);
        super.init();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        removeButton.active = entryListWidget.getSelectedOrNull() != null;
        super.render(matrices, mouseX, mouseY, delta);
    }
}
