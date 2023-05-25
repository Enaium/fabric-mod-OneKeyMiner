package cn.enaium.onekeyminer.command;

import cn.enaium.onekeyminer.OneKeyMiner;
import cn.enaium.onekeyminer.command.argument.ActionArgument;
import cn.enaium.onekeyminer.command.argument.ToolArgument;
import cn.enaium.onekeyminer.model.Action;
import cn.enaium.onekeyminer.model.Tool;
import cn.enaium.onekeyminer.util.BlockUtil;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.command.argument.BlockStateArgumentType;
import net.minecraft.server.command.ServerCommandSource;

import static cn.enaium.onekeyminer.OneKeyMiner.ROOT;
import static net.minecraft.server.command.CommandManager.argument;

/**
 * @author Enaium
 */
public class ActionCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(
                ROOT.then(argument("tool", ToolArgument.tool()).then(argument("action", ActionArgument.action()).then(argument("block", BlockStateArgumentType.blockState(registryAccess)).executes(context -> {

                    final Tool tool = context.getArgument("tool", Tool.class);
                    final Action action = context.getArgument("action", Action.class);
                    final BlockStateArgument block = context.getArgument("block", BlockStateArgument.class);
                    final String blockName = BlockUtil.getName(block.getBlockState().getBlock().getLootTableId());

                    switch (action) {
                        case ADD -> {
                            switch (tool) {
                                case AXE -> OneKeyMiner.config.axe.add(blockName);
                                case HOE -> OneKeyMiner.config.hoe.add(blockName);
                                case PICKAXE -> OneKeyMiner.config.pickaxe.add(blockName);
                                case SHOVEL -> OneKeyMiner.config.shovel.add(blockName);
                                case SHEARS -> OneKeyMiner.config.shears.add(blockName);
                            }
                        }
                        case REMOVE -> {
                            switch (tool) {
                                case AXE -> OneKeyMiner.config.axe.removeIf(s -> s.equals(blockName));
                                case HOE -> OneKeyMiner.config.hoe.removeIf(s -> s.equals(blockName));
                                case PICKAXE -> OneKeyMiner.config.pickaxe.removeIf(s -> s.equals(blockName));
                                case SHOVEL -> OneKeyMiner.config.shovel.removeIf(s -> s.equals(blockName));
                                case SHEARS -> OneKeyMiner.config.shears.removeIf(s -> s.equals(blockName));
                            }
                        }
                    }
                    OneKeyMiner.save();
                    return Command.SINGLE_SUCCESS;
                }))))
        );
    }
}
