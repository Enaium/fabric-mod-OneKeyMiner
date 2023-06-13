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
import cn.enaium.onekeyminer.enums.Action;
import cn.enaium.onekeyminer.enums.Tool;
import cn.enaium.onekeyminer.util.BlockUtil;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.argument.BlockStateArgument;
import net.minecraft.command.argument.BlockStateArgumentType;
import net.minecraft.item.ItemStack;
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
                                        case ANY -> Config.getModel().any.add(blockName);
                                    }
                                    context.getSource().sendFeedback(new TranslatableText("command.action.add.success", new LiteralText(blockName).styled(style -> style.withColor(Formatting.AQUA).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(itemStack))))), false);
                                }
                                case REMOVE -> {
                                    switch (tool) {
                                        case AXE -> Config.getModel().axe.removeIf(s -> s.equals(blockName));
                                        case HOE -> Config.getModel().hoe.removeIf(s -> s.equals(blockName));
                                        case PICKAXE -> Config.getModel().pickaxe.removeIf(s -> s.equals(blockName));
                                        case SHOVEL -> Config.getModel().shovel.removeIf(s -> s.equals(blockName));
                                        case SHEARS -> Config.getModel().shears.removeIf(s -> s.equals(blockName));
                                        case ANY -> Config.getModel().any.removeIf(s -> s.equals(blockName));
                                    }
                                    context.getSource().sendFeedback(new TranslatableText("command.action.remove.success", new LiteralText(blockName).styled(style -> style.withColor(Formatting.AQUA).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(itemStack))))), false);
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
