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

import cn.enaium.onekeyminer.OneKeyMiner;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.Registry;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Enaium
 */
public class BlockListAllScreen extends Screen {

    private ListWidget<BlockListWidget.Entry> entryListWidget;
    private TextFieldWidget textFieldWidget;
    private ButtonWidget addButton;

    private final Screen parent;
    private final List<String> list;

    public BlockListAllScreen(Screen parent, List<String> list) {
        super(new LiteralText(""));
        this.parent = parent;
        this.list = list;
    }

    @Override
    public void init() {
        entryListWidget = new ListWidget<>(client, width, height, 50, height - 50, 24);
        textFieldWidget = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, width / 2 - 100, 15, 200, 20, new LiteralText(""));
        addButton = new ButtonWidget(width / 2 - 100, height - 35, 200, 20, new TranslatableText("button.add"), e -> {
            BlockListWidget.Entry selectedOrNull = entryListWidget.getSelected();
            if (selectedOrNull != null) {
                list.add(selectedOrNull.name);
                OneKeyMiner.save();
                MinecraftClient.getInstance().openScreen(parent);
            }
        });
        get().forEach(entryListWidget::addEntry);
        textFieldWidget.setChangedListener(s -> {
            entryListWidget.replaceEntries(get());
            entryListWidget.setSelected(null);
        });
        addChild(entryListWidget);
        addChild(textFieldWidget);
        addButton(addButton);
        super.init();
    }

    public List<BlockListWidget.Entry> get() {
        return Registry.BLOCK.stream().filter(it -> {
            if (!textFieldWidget.getText().equals("")) {
                return (it.asItem().toString().contains(textFieldWidget.getText()) || new TranslatableText(it.asItem().getTranslationKey()).getString().contains(textFieldWidget.getText()));
            } else {
                return true;
            }
        }).map(it -> new BlockListWidget.Entry(Registry.ITEM.getId(it.asItem()).toString())).collect(Collectors.toList());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        addButton.active = entryListWidget.getSelected() != null;
        entryListWidget.render(matrices, mouseX, mouseY, delta);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
