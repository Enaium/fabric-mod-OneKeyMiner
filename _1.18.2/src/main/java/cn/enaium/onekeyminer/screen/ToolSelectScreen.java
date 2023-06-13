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

/**
 * @author Enaium
 */
public class ToolSelectScreen extends Screen {
    public ToolSelectScreen() {
        super(new LiteralText(""));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);

        addDrawableChild(new ButtonWidget(0, 0, 200, 20, new TranslatableText("button.axe"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListScreen(Config.getModel().axe));
        }));

        addDrawableChild(new ButtonWidget(0, 30, 200, 20, new TranslatableText("button.hoe"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListScreen(Config.getModel().hoe));
        }));

        addDrawableChild(new ButtonWidget(0, 60, 200, 20, new TranslatableText("button.pickaxe"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListScreen(Config.getModel().pickaxe));
        }));

        addDrawableChild(new ButtonWidget(0, 90, 200, 20, new TranslatableText("button.shovel"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListScreen(Config.getModel().shovel));
        }));

        addDrawableChild(new ButtonWidget(0, 120, 200, 20, new TranslatableText("button.shears"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListScreen(Config.getModel().shears));
        }));

        addDrawableChild(new ButtonWidget(0, 120, 200, 20, new TranslatableText("button.any"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListScreen(Config.getModel().any));
        }));

        super.render(matrices, mouseX, mouseY, delta);
    }
}
