package cn.enaium.onekeyminer.command;

import cn.enaium.onekeyminer.Config;
import cn.enaium.onekeyminer.OneKeyMiner;
import cn.enaium.onekeyminer.enums.Tool;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
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
                        list = Config.getModel().axe;
                        break;
                    case HOE:
                        list = Config.getModel().hoe;
                        break;
                    case PICKAXE:
                        list = Config.getModel().pickaxe;
                        break;
                    case SHOVEL:
                        list = Config.getModel().shovel;
                        break;
                    case SHEARS:
                        list = Config.getModel().shears;
                        break;
                }

                Text previous = null;
                for (int i = 0; i < list.size(); i++) {
                    int finalI = i;
                    List<String> finalList = list;
                    final Text item = new LiteralText(list.get(i))
                            .styled(style -> style.setHoverEvent(
                                    new HoverEvent(
                                            HoverEvent.Action.SHOW_TEXT,
                                            new TranslatableText(Registry.BLOCK.get(new Identifier(finalList.get(finalI))).getTranslationKey())
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