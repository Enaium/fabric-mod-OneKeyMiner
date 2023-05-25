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

import cn.enaium.onekeyminer.command.ActionCommand;
import cn.enaium.onekeyminer.command.ListCommand;
import cn.enaium.onekeyminer.command.ScreenCommand;
import cn.enaium.onekeyminer.command.argument.ActionArgument;
import cn.enaium.onekeyminer.command.argument.ToolArgument;
import cn.enaium.onekeyminer.model.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.Identifier;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static net.minecraft.server.command.CommandManager.literal;

public class OneKeyMiner implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("OneKeyMiner");
    private static final File configFile = new File(MinecraftClient.getInstance().runDirectory, "OneKeyMiner.json");
    public static Config config = new Config();

    public static final LiteralArgumentBuilder<ServerCommandSource> ROOT = literal("onekeyminer").requires(source -> source.hasPermissionLevel(4));

    public static void load() {
        if (configFile.exists()) {
            try {
                config = new Gson().fromJson(FileUtils.readFileToString(configFile, StandardCharsets.UTF_8), Config.class);
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
        } else {
            save();
        }
    }

    public static void save() {
        try {
            FileUtils.write(configFile, new GsonBuilder().setPrettyPrinting().create().toJson(config), StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Hello OneKeyMiner world!");

        ArgumentTypeRegistry.registerArgumentType(new Identifier("onekeyminer", "tool"), ToolArgument.class, ConstantArgumentSerializer.of(ToolArgument::tool));
        ArgumentTypeRegistry.registerArgumentType(new Identifier("onekeyminer", "action"), ActionArgument.class, ConstantArgumentSerializer.of(ActionArgument::action));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            ListCommand.register(dispatcher);
            ActionCommand.register(dispatcher, registryAccess);
            ScreenCommand.register(dispatcher);
        });

        load();
        Runtime.getRuntime().addShutdownHook(new Thread(OneKeyMiner::save));
    }
}
