package cn.enaium.onekeyminer.command;

import cn.enaium.onekeyminer.Config;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

import static cn.enaium.onekeyminer.OneKeyMiner.ROOT;

/**
 * @author Enaium
 */
public class LimitCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(ROOT.then(CommandManager.literal("limit").executes(context -> {
            context.getSource().sendFeedback(new TranslatableText("command.limit.get", Config.getModel().limit), false);
            return Command.SINGLE_SUCCESS;
        }).then(CommandManager.argument("limit", IntegerArgumentType.integer()).executes(context -> {
            int limit = IntegerArgumentType.getInteger(context, "limit");
            if (limit <= 0) {
                context.getSource().sendError(new TranslatableText("command.limit.error"));
                return Command.SINGLE_SUCCESS;
            }
            Config.getModel().limit = limit;

            context.getSource().sendFeedback(new TranslatableText("command.limit.success", limit), false);
            return Command.SINGLE_SUCCESS;
        }))));
    }
}
