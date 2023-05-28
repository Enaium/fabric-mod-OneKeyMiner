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
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

/**
 * @author Enaium
 */
public class ToolSelectScreen extends Screen {
    public ToolSelectScreen() {
        super(Text.literal(""));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);

        addDrawableChild(ButtonWidget.builder(Text.translatable("axe"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListScreen(OneKeyMiner.config.axe));
        }).dimensions(0, 0, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.translatable("hoe"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListScreen(OneKeyMiner.config.hoe));
        }).dimensions(0, 30, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.translatable("pickaxe"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListScreen(OneKeyMiner.config.pickaxe));
        }).dimensions(0, 60, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.translatable("shovel"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListScreen(OneKeyMiner.config.shovel));
        }).dimensions(0, 90, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.translatable("shears"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListScreen(OneKeyMiner.config.shears));
        }).dimensions(0, 120, 200, 20).build());

        super.render(matrices, mouseX, mouseY, delta);
    }
}
