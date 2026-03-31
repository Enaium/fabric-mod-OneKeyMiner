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

package cn.enaium.onekeyminer

import cn.enaium.onekeyminer.config.OneKeyMinerConfig
import net.minecraft.block.Block

/**
 * @author Enaium
 */
object Commands {
    @JvmStatic
    fun initializer() {
        val map = Block.REGISTRY.keySet.map { it.toString() }
        OneKeyMinerConfig.axe = OneKeyMinerConfig.axe.copy(options = map)
        OneKeyMinerConfig.hoe = OneKeyMinerConfig.hoe.copy(options = map)
        OneKeyMinerConfig.pickaxe = OneKeyMinerConfig.pickaxe.copy(options = map)
        OneKeyMinerConfig.shovel = OneKeyMinerConfig.shovel.copy(options = map)
        OneKeyMinerConfig.shears = OneKeyMinerConfig.shears.copy(options = map)
        OneKeyMinerConfig.any = OneKeyMinerConfig.any.copy(options = map)
    }
}