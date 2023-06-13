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

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Enaium
 */
public class Config {

    private static final File configFile = new File(System.getProperty("user.dir"), "OneKeyMiner.json");
    private static Model model = new Model();

    public static Model getModel() {
        return model;
    }

    public static void load() {
        if (configFile.exists()) {
            try {
                model = new Gson().fromJson(FileUtils.readFileToString(configFile, StandardCharsets.UTF_8), Model.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            save();
        }
    }

    public static void save() {
        try {
            FileUtils.write(configFile, new GsonBuilder().setPrettyPrinting().create().toJson(model), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class Model {
        public int limit = 64;
        public List<String> axe = new ArrayList<>(ImmutableList.of(
                "minecraft:warped_stem",
                "minecraft:crimson_stem",
                "minecraft:oak_log",
                "minecraft:birch_log",
                "minecraft:spruce_log",
                "minecraft:jungle_log",
                "minecraft:dark_oak_log",
                "minecraft:acacia_log",
                "minecraft:oak_leaves",
                "minecraft:spruce_leaves",
                "minecraft:birch_leaves",
                "minecraft:jungle_leaves",
                "minecraft:acacia_leaves",
                "minecraft:dark_oak_leaves"
        ));
        public List<String> hoe = new ArrayList<>(ImmutableList.of(
                "minecraft:oak_leaves",
                "minecraft:spruce_leaves",
                "minecraft:birch_leaves",
                "minecraft:jungle_leaves",
                "minecraft:acacia_leaves",
                "minecraft:dark_oak_leaves",
                "minecraft:sponge",
                "minecraft:wet_sponge",
                "minecraft:hay_block",
                "minecraft:nether_wart_block",
                "minecraft:dried_kelp_block",
                "minecraft:warped_wart_block",
                "minecraft:shroomlight"
        ));
        public List<String> pickaxe = new ArrayList<>(ImmutableList.of(
                "minecraft:gold_ore",
                "minecraft:iron_ore",
                "minecraft:coal_ore",
                "minecraft:nether_gold_ore",
                "minecraft:lapis_ore",
                "minecraft:diamond_ore",
                "minecraft:redstone_ore",
                "minecraft:emerald_ore",
                "minecraft:nether_quartz_ore"
        ));
        public List<String> shovel = new ArrayList<>(ImmutableList.of(
                "minecraft:sand",
                "minecraft:red_sand",
                "minecraft:snow",
                "minecraft:snow_block",
                "minecraft:clay",
                "minecraft:soul_sand"
        ));
        public List<String> shears = new ArrayList<>(ImmutableList.of(
                "minecraft:oak_leaves",
                "minecraft:spruce_leaves",
                "minecraft:birch_leaves",
                "minecraft:jungle_leaves",
                "minecraft:acacia_leaves",
                "minecraft:dark_oak_leaves"
        ));

        public List<String> any = new ArrayList<>();
    }
}
