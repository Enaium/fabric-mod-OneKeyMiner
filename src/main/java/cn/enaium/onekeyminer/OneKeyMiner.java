/**
 * Copyright 2022 Enaium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.enaium.onekeyminer;

import cn.enaium.onekeyminer.model.Config;
import cn.enaium.onekeyminer.screen.ToolSelectScreen;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.brigadier.Command;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.command.CommandManager;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class OneKeyMiner implements ModInitializer {

    @Override
    public void onInitialize() {
        System.out.println("Hello OneKeyMiner world!");

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            dispatcher.register(CommandManager.literal("onekeyminer").executes(context -> {
                MinecraftClient.getInstance().execute(() -> MinecraftClient.getInstance().openScreen(new ToolSelectScreen()));
                return Command.SINGLE_SUCCESS;
            }));
        });

        load();
        Runtime.getRuntime().addShutdownHook(new Thread(OneKeyMiner::save));
    }

    private static final File configFile = new File(MinecraftClient.getInstance().runDirectory, "OneKeyMiner.json");
    public static Config config = new Config();

    public static void load() {
        if (configFile.exists()) {
            try {
                config = new Gson().fromJson(FileUtils.readFileToString(configFile, StandardCharsets.UTF_8), Config.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            save();
        }
    }

    public static void save() {
        try {
            FileUtils.write(configFile, new GsonBuilder().setPrettyPrinting().create().toJson(config), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
