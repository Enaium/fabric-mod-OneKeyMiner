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
