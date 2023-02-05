/**
 * Copyright (C) 2022 Enaium
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
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
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Enaium
 */
public class BlockListAllScreen extends Screen {

    private final Screen parent;
    private final List<String> list;
    private ListWidget<BlockListWidget.Entry> entryListWidget;
    private TextFieldWidget textFieldWidget;
    private ButtonWidget addButton;

    public BlockListAllScreen(Screen parent, List<String> list) {
        super(Text.literal(""));
        this.parent = parent;
        this.list = list;
    }

    @Override
    public void init() {
        entryListWidget = new ListWidget<>(client, width, height, 50, height - 50, 24);
        textFieldWidget = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, width / 2 - 100, 15, 200, 20, Text.literal(""));
        addButton = ButtonWidget.builder(Text.translatable("button.add"), e -> {
            BlockListWidget.Entry selectedOrNull = entryListWidget.getSelectedOrNull();
            if (selectedOrNull != null) {
                list.add(selectedOrNull.name);
                OneKeyMiner.save();
                MinecraftClient.getInstance().setScreen(parent);
            }
        }).dimensions(width / 2 - 100, height - 35, 200, 20).build();
        get().forEach(entryListWidget::addEntry);
        textFieldWidget.setChangedListener(s -> {
            entryListWidget.replaceEntries(get());
            entryListWidget.setSelected(null);
        });
        addDrawableChild(entryListWidget);
        addDrawableChild(textFieldWidget);
        addDrawableChild(addButton);
        super.init();
    }

    public List<BlockListWidget.Entry> get() {
        return Registries.BLOCK.stream().filter(it -> {
            if (!textFieldWidget.getText().equals("")) {
                return (it.asItem().toString().contains(textFieldWidget.getText()) || Text.translatable(it.asItem().getTranslationKey()).getString().contains(textFieldWidget.getText()));
            } else {
                return true;
            }
        }).map(it -> new BlockListWidget.Entry(Registries.ITEM.getId(it.asItem()).toString())).collect(Collectors.toList());
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        addButton.active = entryListWidget.getSelectedOrNull() != null;
        super.render(matrices, mouseX, mouseY, delta);
    }
}
