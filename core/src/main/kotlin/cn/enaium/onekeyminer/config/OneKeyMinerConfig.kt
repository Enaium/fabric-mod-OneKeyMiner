/*
 * Copyright 2026 Enaium
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

package cn.enaium.onekeyminer.config

import cn.enaium.mineconf.core.ConfBuilder

/**
 * @author Enaium
 */
object OneKeyMinerConfig {
    var limit = ConfBuilder
        .create().id("limit").name("Limit")
        .description("Sets the max number of blocks mined per action")
        .literal<Int>().build(64)

    var interact = ConfBuilder
        .create().id("interact").name("Interact")
        .description("Sets whether to interact with the block when mining")
        .literal<Boolean>().build(false)

    var hotkey = ConfBuilder
        .create().id("hotkey").name("Hotkey")
        .description("Enables/dissabes active key to trigger the miner")
        .literal<Boolean>().build(true)

    var axe = ConfBuilder
        .create().id("axe").name("Axe")
        .description("Blocks to mine with the axe")
        .collection<String>().build(mutableListOf())

    var hoe = ConfBuilder
        .create().id("hoe").name("Hoe")
        .description("Blocks to mine with the hoe")
        .collection<String>().build(mutableListOf())

    var pickaxe = ConfBuilder
        .create().id("pickaxe").name("Pickaxe")
        .description("Blocks to mine with the pickaxe")
        .collection<String>().build(mutableListOf())

    var shovel = ConfBuilder
        .create().id("shovel").name("Shovel")
        .description("Blocks to mine with the shovel")
        .collection<String>().build(mutableListOf())

    var shears = ConfBuilder
        .create().id("shears").name("Shears")
        .description("Blocks to mine with the shears")
        .collection<String>().build(mutableListOf())

    var any = ConfBuilder
        .create().id("any").name("Any")
        .description("Blocks to mine with any tool")
        .collection<String>().build(mutableListOf())
}