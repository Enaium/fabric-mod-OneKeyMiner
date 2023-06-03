/*
 * Copyright 2022 Enaium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.enaium.onekeyminer.command;

import cn.enaium.onekeyminer.Config;
import cn.enaium.onekeyminer.enums.Tool;
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
                    case AXE -> Config.getModel().axe;
                    case HOE -> Config.getModel().hoe;
                    case PICKAXE -> Config.getModel().pickaxe;
                    case SHOVEL -> Config.getModel().shovel;
                    case SHEARS -> Config.getModel().shears;
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
