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
package cn.enaium.onekeyminer

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.IOException
import kotlin.io.path.*

/**
 * @author Enaium
 */
object Config {
    private val configFile = Path(System.getProperty("user.dir")).resolve("config").resolve("OneKeyMiner.json")

    var model = Model()
        private set

    fun load() {
        if (configFile.exists()) {
            try {
                model =
                    Gson().fromJson(configFile.readText(Charsets.UTF_8), Model::class.java)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            save()
        }
    }

    fun save() {
        try {
            configFile.createParentDirectories()
            configFile.writeText(GsonBuilder().setPrettyPrinting().create().toJson(model), Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    class Model {
        var limit = 64
        var interact = false
        var axe: MutableList<String> = mutableListOf(
            "minecraft:warped_stem",
            "minecraft:crimson_stem",
            "minecraft:oak_log",
            "minecraft:birch_log",
            "minecraft:spruce_log",
            "minecraft:jungle_log",
            "minecraft:dark_oak_log",
            "minecraft:acacia_log",
            "minecraft:cherry_log",
            "minecraft:oak_leaves",
            "minecraft:spruce_leaves",
            "minecraft:birch_leaves",
            "minecraft:jungle_leaves",
            "minecraft:acacia_leaves",
            "minecraft:dark_oak_leaves",
            "minecraft:azalea_leaves",
            "minecraft:flowering_azalea_leaves",
        )

        var hoe: MutableList<String> = mutableListOf(
            "minecraft:oak_leaves",
            "minecraft:spruce_leaves",
            "minecraft:birch_leaves",
            "minecraft:jungle_leaves",
            "minecraft:acacia_leaves",
            "minecraft:dark_oak_leaves",
            "minecraft:azalea_leaves",
            "minecraft:flowering_azalea_leaves",
            "minecraft:sponge",
            "minecraft:wet_sponge",
            "minecraft:hay_block",
            "minecraft:nether_wart_block",
            "minecraft:dried_kelp_block",
            "minecraft:warped_wart_block",
            "minecraft:shroomlight",
            "minecraft:sculk_sensor",
            "minecraft:moss_carpet",
            "minecraft:moss_block"
        )

        var pickaxe: MutableList<String> = mutableListOf(
            "minecraft:gold_ore",
            "minecraft:deepslate_gold_ore",
            "minecraft:iron_ore",
            "minecraft:deepslate_iron_ore",
            "minecraft:coal_ore",
            "minecraft:deepslate_coal_ore",
            "minecraft:nether_gold_ore",
            "minecraft:lapis_ore",
            "minecraft:deepslate_lapis_ore",
            "minecraft:diamond_ore",
            "minecraft:deepslate_diamond_ore",
            "minecraft:redstone_ore",
            "minecraft:deepslate_redstone_ore",
            "minecraft:emerald_ore",
            "minecraft:deepslate_emerald_ore",
            "minecraft:nether_quartz_ore",
            "minecraft:copper_ore",
            "minecraft:deepslate_copper_ore"
        )

        var shovel: MutableList<String> = mutableListOf(
            "minecraft:sand",
            "minecraft:red_sand",
            "minecraft:snow",
            "minecraft:snow_block",
            "minecraft:clay",
            "minecraft:soul_sand"
        )

        var shears: MutableList<String> = mutableListOf(
            "minecraft:oak_leaves",
            "minecraft:spruce_leaves",
            "minecraft:birch_leaves",
            "minecraft:jungle_leaves",
            "minecraft:acacia_leaves",
            "minecraft:dark_oak_leaves",
            "minecraft:azalea_leaves",
            "minecraft:flowering_azalea_leaves",
        )

        var any: MutableList<String> = mutableListOf()
    }
}
