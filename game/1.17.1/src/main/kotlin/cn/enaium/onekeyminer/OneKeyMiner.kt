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

import cn.enaium.onekeyminer.Config.model
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import net.minecraft.server.command.CommandManager.literal
import net.minecraft.server.command.ServerCommandSource

/**
 * @author Enaium
 */
const val ID = "onekeyminer"

val ROOT: LiteralArgumentBuilder<ServerCommandSource> = literal(ID).requires { source ->
    source.hasPermissionLevel(4)
}

fun initializer() {
    if (model.axe.isEmpty()) {
        model.axe = mutableListOf(
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
    }

    if (model.hoe.isEmpty()) {
        model.hoe = mutableListOf(
            "minecraft:oak_leaves",
            "minecraft:spruce_leaves",
            "minecraft:birch_leaves",
            "minecraft:jungle_leaves",
            "minecraft:acacia_leaves",
            "minecraft:dark_oak_leaves",
            "minecraft:azalea_leaves",
            "minecraft:flowering_azalea_leaves",
            "minecraft:cherry_leaves",
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
    }

    if (model.pickaxe.isEmpty()) {

        model.pickaxe = mutableListOf(
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
    }

    if (model.shovel.isEmpty()) {
        model.shovel = mutableListOf(
            "minecraft:sand",
            "minecraft:red_sand",
            "minecraft:snow",
            "minecraft:snow_block",
            "minecraft:clay",
            "minecraft:soul_sand"
        )
    }

    if (model.shears.isEmpty()) {
        model.shears = mutableListOf(
            "minecraft:oak_leaves",
            "minecraft:spruce_leaves",
            "minecraft:birch_leaves",
            "minecraft:jungle_leaves",
            "minecraft:acacia_leaves",
            "minecraft:dark_oak_leaves",
            "minecraft:azalea_leaves",
            "minecraft:flowering_azalea_leaves"
        )
    }
}