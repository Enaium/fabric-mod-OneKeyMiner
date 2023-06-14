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
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

/**
 * @author Enaium
 */
public class ToolSelectScreen extends Screen {
    public ToolSelectScreen() {
        super(Text.literal(""));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        addDrawableChild(ButtonWidget.builder(Text.translatable("button.axe"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListScreen(Config.getModel().axe));
        }).dimensions(0, 0, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.translatable("button.hoe"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListScreen(Config.getModel().hoe));
        }).dimensions(0, 30, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.translatable("button.pickaxe"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListScreen(Config.getModel().pickaxe));
        }).dimensions(0, 60, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.translatable("button.shovel"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListScreen(Config.getModel().shovel));
        }).dimensions(0, 90, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.translatable("button.shears"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListScreen(Config.getModel().shears));
        }).dimensions(0, 120, 200, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.translatable("button.any"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListScreen(Config.getModel().any));
        }).dimensions(0, 150, 200, 20).build());
        super.render(context, mouseX, mouseY, delta);
    }
}