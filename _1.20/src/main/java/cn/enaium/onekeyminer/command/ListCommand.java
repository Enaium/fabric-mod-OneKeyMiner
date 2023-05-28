package cn.enaium.onekeyminer.command;

import cn.enaium.onekeyminer.OneKeyMiner;
import cn.enaium.onekeyminer.model.Tool;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.registry.Registries;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

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
                final List<String> list = switch (tool) {
                    case AXE -> OneKeyMiner.config.axe;
                    case HOE -> OneKeyMiner.config.hoe;
                    case PICKAXE -> OneKeyMiner.config.pickaxe;
                    case SHOVEL -> OneKeyMiner.config.shovel;
                    case SHEARS -> OneKeyMiner.config.shears;
                };
                MutableText previous = null;
                for (int i = 0; i < list.size(); i++) {
                    int finalI = i;
                    final MutableText item = Text.literal(list.get(i))
                            .styled(style -> style.withHoverEvent(
                                    new HoverEvent(
                                            HoverEvent.Action.SHOW_ITEM,
                                            new HoverEvent.ItemStackContent(Registries.BLOCK.get(new Identifier(list.get(finalI))).asItem().getDefaultStack())
                                    )
                            ));
                    if (previous == null) {
                        previous = item;
                    } else {
                        previous.append(Text.literal(", "));
                        previous.append(item);
                    }
                }
                MutableText finalPrevious = previous;
                context.getSource().sendFeedback(() -> finalPrevious, false);
                return Command.SINGLE_SUCCESS;
            }))));
        }
    }
}
