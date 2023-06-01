package cn.enaium.onekeyminer.command;

import cn.enaium.onekeyminer.Config;
import cn.enaium.onekeyminer.enums.Action;
import cn.enaium.onekeyminer.enums.Tool;
import cn.enaium.onekeyminer.util.BlockUtil;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.arguments.BlockStateArgument;
import net.minecraft.command.arguments.BlockStateArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import static cn.enaium.onekeyminer.OneKeyMiner.ROOT;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

/**
 * @author Enaium
 */
public class ActionCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        for (Tool tool : Tool.values()) {
            for (Action action : Action.values()) {
                dispatcher.register(
                        ROOT.then(literal(tool.name()).then(literal(action.name()).then(argument("block", BlockStateArgumentType.blockState()).executes(context -> {
                            final BlockStateArgument block = context.getArgument("block", BlockStateArgument.class);
                            final String blockName = BlockUtil.getName(block.getBlockState().getBlock().getDropTableId());

                            switch (action) {
                                case ADD:
                                    switch (tool) {
                                        case AXE:
                                            Config.getModel().axe.add(blockName);
                                        case HOE:
                                            Config.getModel().hoe.add(blockName);
                                        case PICKAXE:
                                            Config.getModel().pickaxe.add(blockName);
                                        case SHOVEL:
                                            Config.getModel().shovel.add(blockName);
                                        case SHEARS:
                                            Config.getModel().shears.add(blockName);
                                    }
                                    context.getSource().sendFeedback(new TranslatableText("command.action.add.success", new LiteralText(blockName).styled(style -> style.setColor(Formatting.AQUA).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText(block.getBlockState().getBlock().getTranslationKey()))))), false);
                                    break;
                                case REMOVE:
                                    switch (tool) {
                                        case AXE:
                                            Config.getModel().axe.removeIf(s -> s.equals(blockName));
                                        case HOE:
                                            Config.getModel().hoe.removeIf(s -> s.equals(blockName));
                                        case PICKAXE:
                                            Config.getModel().pickaxe.removeIf(s -> s.equals(blockName));
                                        case SHOVEL:
                                            Config.getModel().shovel.removeIf(s -> s.equals(blockName));
                                        case SHEARS:
                                            Config.getModel().shears.removeIf(s -> s.equals(blockName));
                                    }
                                    context.getSource().sendFeedback(new TranslatableText("command.action.remove.success", new LiteralText(blockName).styled(style -> style.setColor(Formatting.AQUA).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText(block.getBlockState().getBlock().getTranslationKey()))))), false);
                                    break;
                            }
                            Config.save();
                            return Command.SINGLE_SUCCESS;
                        }))))
                );
            }
        }
    }
}