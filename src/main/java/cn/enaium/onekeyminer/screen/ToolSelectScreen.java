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

        addButton(new ButtonWidget(0, 0, 200, 20, new TranslatableText("axe"), e -> {
            MinecraftClient.getInstance().openScreen(new BlockListScreen(OneKeyMiner.config.axe));
        }));

        addButton(new ButtonWidget(0, 30, 200, 20, new TranslatableText("hoe"), e -> {
            MinecraftClient.getInstance().openScreen(new BlockListScreen(OneKeyMiner.config.hoe));
        }));

        addButton(new ButtonWidget(0, 60, 200, 20, new TranslatableText("pickaxe"), e -> {
            MinecraftClient.getInstance().openScreen(new BlockListScreen(OneKeyMiner.config.pickaxe));
        }));

        addButton(new ButtonWidget(0, 90, 200, 20, new TranslatableText("shovel"), e -> {
            MinecraftClient.getInstance().openScreen(new BlockListScreen(OneKeyMiner.config.shovel));
        }));

        addButton(new ButtonWidget(0, 120, 200, 20, new TranslatableText("shears"), e -> {
            MinecraftClient.getInstance().openScreen(new BlockListScreen(OneKeyMiner.config.shears));
        }));

        super.render(matrices, mouseX, mouseY, delta);
    }
}
