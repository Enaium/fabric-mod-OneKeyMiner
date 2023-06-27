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
import java.io.File
import java.io.IOException

/**
 * @author Enaium
 */
object Config {
    private val configFile = File(System.getProperty("user.dir"), "OneKeyMiner.json")

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
            configFile.writeText(GsonBuilder().setPrettyPrinting().create().toJson(model), Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    class Model {
        var limit = 64
        var interact = false
        var axe: MutableList<String> = mutableListOf(
            "minecraft:log",
            "minecraft:log2",
            "minecraft:leaves",
            "minecraft:leaves2"
        )

        var hoe: MutableList<String> = mutableListOf(
            "minecraft:sponge",
            "minecraft:hay_block",
            "minecraft:nether_wart_block",
            "minecraft:leaves",
            "minecraft:leaves2"
        )

        var pickaxe: MutableList<String> = mutableListOf(
            "minecraft:gold_ore",
            "minecraft:iron_ore",
            "minecraft:coal_ore",
            "minecraft:lapis_ore",
            "minecraft:diamond_ore",
            "minecraft:redstone_ore",
            "minecraft:emerald_ore"
        )

        var shovel: MutableList<String> = mutableListOf(
            "minecraft:sand",
            "minecraft:snow",
            "minecraft:clay",
            "minecraft:soul_sand"
        )

        var shears: MutableList<String> = mutableListOf(
            "minecraft:leaves",
            "minecraft:leaves2"
        )

        var any: MutableList<String> = mutableListOf()
    }
}
