package cn.enaium.onekeyminer.command;

import cn.enaium.onekeyminer.Config;
import cn.enaium.onekeyminer.enums.Action;
import cn.enaium.onekeyminer.enums.Tool;
import cn.enaium.onekeyminer.util.BlockUtil;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.command.argument.BlockStateArgumentType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import static cn.enaium.onekeyminer.OneKeyMiner.ROOT;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

/**
 * @author Enaium
 */
public class ActionCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        for (Tool tool : Tool.values()) {
            for (Action action : Action.values()) {
                dispatcher.register(
                        ROOT.then(literal(tool.name()).then(literal(action.name()).then(argument("block", BlockStateArgumentType.blockState(registryAccess)).executes(context -> {
                            final BlockStateArgument block = context.getArgument("block", BlockStateArgument.class);
                            final ItemStack itemStack = block.getBlockState().getBlock().asItem().getDefaultStack();
                            final String blockName = BlockUtil.getName(block.getBlockState().getBlock().getLootTableId());

                            switch (action) {
                                case ADD -> {
                                    switch (tool) {
                                        case AXE -> Config.getModel().axe.add(blockName);
                                        case HOE -> Config.getModel().hoe.add(blockName);
                                        case PICKAXE -> Config.getModel().pickaxe.add(blockName);
                                        case SHOVEL -> Config.getModel().shovel.add(blockName);
                                        case SHEARS -> Config.getModel().shears.add(blockName);
                                    }
                                    context.getSource().sendFeedback(Text.translatable("command.action.add.success", Text.literal(blockName).styled(style -> style.withColor(Formatting.AQUA).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(itemStack))))), false);
                                }
                                case REMOVE -> {
                                    switch (tool) {
                                        case AXE -> Config.getModel().axe.removeIf(s -> s.equals(blockName));
                                        case HOE -> Config.getModel().hoe.removeIf(s -> s.equals(blockName));
                                        case PICKAXE -> Config.getModel().pickaxe.removeIf(s -> s.equals(blockName));
                                        case SHOVEL -> Config.getModel().shovel.removeIf(s -> s.equals(blockName));
                                        case SHEARS -> Config.getModel().shears.removeIf(s -> s.equals(blockName));
                                    }
                                    context.getSource().sendFeedback(Text.translatable("command.action.remove.success", Text.literal(blockName).styled(style -> style.withColor(Formatting.AQUA).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(itemStack))))), false);
                                }
                            }
                            Config.save();
                            return Command.SINGLE_SUCCESS;
                        }))))
                );
            }
        }
    }
}
