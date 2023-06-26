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
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
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
                    case ANY:
                        list = Config.getModel().any;
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