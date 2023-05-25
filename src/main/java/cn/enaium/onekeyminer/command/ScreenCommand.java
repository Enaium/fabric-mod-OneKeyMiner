package cn.enaium.onekeyminer.command;

import cn.enaium.onekeyminer.screen.ToolSelectScreen;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import static cn.enaium.onekeyminer.OneKeyMiner.ROOT;

/**
 * @author Enaium
 */
public class ScreenCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(ROOT.then(CommandManager.literal("screen").executes(context -> {
            MinecraftClient.getInstance().execute(() -> MinecraftClient.getInstance().setScreen(new ToolSelectScreen()));
            return Command.SINGLE_SUCCESS;
        })));
    }
}
