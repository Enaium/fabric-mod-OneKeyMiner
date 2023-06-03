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

import cn.enaium.onekeyminer.screen.ToolSelectScreen;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import static cn.enaium.onekeyminer.OneKeyMiner.ROOT;

/**
 * @author Enaium
 */
public class ScreenCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(ROOT.then(CommandManager.literal("screen").executes(context -> {
            if (context.getSource().getPlayer() != null && MinecraftClient.getInstance().player != null) {
                if (context.getSource().getPlayer().getUuid().equals(MinecraftClient.getInstance().player.getUuid())) {
                    MinecraftClient.getInstance().execute(() -> MinecraftClient.getInstance().setScreen(new ToolSelectScreen()));
                }
            }
            return Command.SINGLE_SUCCESS;
        })));
    }
}
