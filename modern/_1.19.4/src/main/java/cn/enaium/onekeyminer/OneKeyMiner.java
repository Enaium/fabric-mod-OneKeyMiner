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
package cn.enaium.onekeyminer;

import cn.enaium.onekeyminer.callback.FinishMiningCallback;
import cn.enaium.onekeyminer.callback.UseOnBlockCallback;
import cn.enaium.onekeyminer.callback.impl.FinishMiningCallbackImpl;
import cn.enaium.onekeyminer.callback.impl.UseOnBlockCallbackImpl;
import cn.enaium.onekeyminer.command.ActionCommand;
import cn.enaium.onekeyminer.command.InteractCommand;
import cn.enaium.onekeyminer.command.LimitCommand;
import cn.enaium.onekeyminer.command.ListCommand;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.server.command.CommandManager.literal;

public class OneKeyMiner implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("OneKeyMiner");
    public static final LiteralArgumentBuilder<ServerCommandSource> ROOT = literal("onekeyminer").requires(source -> source.hasPermissionLevel(4));

    @Override
    public void onInitialize() {
        LOGGER.info("Hello OneKeyMiner world!");

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            ListCommand.register(dispatcher);
            LimitCommand.register(dispatcher);
            ActionCommand.register(dispatcher, registryAccess);
            InteractCommand.register(dispatcher);
        });

        FinishMiningCallback.EVENT.register(new FinishMiningCallbackImpl());
        UseOnBlockCallback.EVENT.register(new UseOnBlockCallbackImpl());

        Config.load();
        Runtime.getRuntime().addShutdownHook(new Thread(Config::save));
    }
}
