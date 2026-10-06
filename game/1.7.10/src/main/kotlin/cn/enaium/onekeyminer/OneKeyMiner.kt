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

import cn.enaium.onekeyminer.config.OneKeyMinerConfig

/**
 * @author Enaium
 */
const val ID = "onekeyminer"

fun initializer() {
    // The built-in blocks are the default value of the conf, so a reset restores them.
    OneKeyMinerConfig.axe = OneKeyMinerConfig.axe.copy(
        defaultValue = mutableListOf(
            "minecraft:log",
            "minecraft:log2",
            "minecraft:leaves",
            "minecraft:leaves2"
        )
    )

    // The built-in blocks are the default value of the conf, so a reset restores them.
    OneKeyMinerConfig.hoe = OneKeyMinerConfig.hoe.copy(
        defaultValue = mutableListOf(
            "minecraft:sponge",
            "minecraft:hay_block",
            "minecraft:nether_wart_block",
            "minecraft:leaves",
            "minecraft:leaves2"
        )
    )

    // The built-in blocks are the default value of the conf, so a reset restores them.
    OneKeyMinerConfig.pickaxe = OneKeyMinerConfig.pickaxe.copy(
        defaultValue = mutableListOf(
            "minecraft:gold_ore",
            "minecraft:iron_ore",
            "minecraft:coal_ore",
            "minecraft:lapis_ore",
            "minecraft:diamond_ore",
            "minecraft:redstone_ore",
            "minecraft:emerald_ore"
        )
    )

    // The built-in blocks are the default value of the conf, so a reset restores them.
    OneKeyMinerConfig.shovel = OneKeyMinerConfig.shovel.copy(
        defaultValue = mutableListOf(
            "minecraft:sand",
            "minecraft:snow",
            "minecraft:clay",
            "minecraft:soul_sand"
        )
    )

    // The built-in blocks are the default value of the conf, so a reset restores them.
    OneKeyMinerConfig.shears = OneKeyMinerConfig.shears.copy(
        defaultValue = mutableListOf(
            "minecraft:leaves",
            "minecraft:leaves2"
        )
    )

    // The built-in blocks are the default value of the conf, so a reset restores them.
    OneKeyMinerConfig.any = OneKeyMinerConfig.any.copy(
        defaultValue = mutableListOf(
            "minecraft:leaves",
            "minecraft:leaves2"
        )
    )
}