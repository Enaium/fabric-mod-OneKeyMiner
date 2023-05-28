package cn.enaium.onekeyminer.screen;

import cn.enaium.onekeyminer.OneKeyMiner;
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

        addDrawableChild(new ButtonWidget(0, 0, 200, 20, new TranslatableText("axe"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListScreen(OneKeyMiner.config.axe));
        }));

        addDrawableChild(new ButtonWidget(0, 30, 200, 20, new TranslatableText("hoe"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListScreen(OneKeyMiner.config.hoe));

        }));

        addDrawableChild(new ButtonWidget(0, 60, 200, 20, new TranslatableText("pickaxe"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListScreen(OneKeyMiner.config.pickaxe));

        }));

        addDrawableChild(new ButtonWidget(0, 90, 200, 20, new TranslatableText("shovel"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListScreen(OneKeyMiner.config.shovel));

        }));

        addDrawableChild(new ButtonWidget(0, 120, 200, 20, new TranslatableText("shears"), e -> {
            MinecraftClient.getInstance().setScreen(new BlockListScreen(OneKeyMiner.config.shears));

        }));

        super.render(matrices, mouseX, mouseY, delta);
    }
}
