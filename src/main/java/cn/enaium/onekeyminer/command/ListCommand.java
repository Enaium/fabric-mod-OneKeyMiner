package cn.enaium.onekeyminer.command;

import cn.enaium.onekeyminer.OneKeyMiner;
import cn.enaium.onekeyminer.model.Tool;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

import static cn.enaium.onekeyminer.OneKeyMiner.ROOT;
import static net.minecraft.server.command.CommandManager.literal;

/**
 * @author Enaium
 */
public class ListCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        for (Tool tool : Tool.values()) {
            dispatcher.register(ROOT.then(literal(tool.name()).then(literal("list").executes(context -> {
                List<String> list = new ArrayList<>();

                switch (tool) {
                    case AXE:
                        list = OneKeyMiner.config.axe;
                        break;
                    case HOE:
                        list = OneKeyMiner.config.hoe;
                        break;
                    case PICKAXE:
                        list = OneKeyMiner.config.pickaxe;
                        break;
                    case SHOVEL:
                        list = OneKeyMiner.config.shovel;
                        break;
                    case SHEARS:
                        list = OneKeyMiner.config.shears;
                        break;
                }

                MutableText previous = null;
                for (int i = 0; i < list.size(); i++) {
                    int finalI = i;
                    List<String> finalList = list;
                    final MutableText item = new LiteralText(list.get(i))
                            .styled(style -> style.withHoverEvent(
                                    new HoverEvent(
                                            HoverEvent.Action.SHOW_ITEM,
                                            new HoverEvent.ItemStackContent(Registry.BLOCK.get(new Identifier(finalList.get(finalI))).asItem().getDefaultStack())
                                    )
                            ));
                    if (previous == null) {
                        previous = item;
                    } else {
                        previous.append(new LiteralText(", "));
                        previous.append(item);
                    }
                }
                context.getSource().sendFeedback(previous, false);
                return Command.SINGLE_SUCCESS;
            }))));
        }
    }
}