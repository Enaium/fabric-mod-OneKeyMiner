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
import com.mojang.brigadier.CommandDispatcher
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.registry.RegistryKeys
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import kotlin.jvm.optionals.getOrNull

/**
 * @author Enaium
 */
object Commands {
    @JvmStatic
    fun initializer() {
        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher: CommandDispatcher<ServerCommandSource>, registryAccess: CommandRegistryAccess, environment: CommandManager.RegistrationEnvironment ->
            val map =
                registryAccess.getOptional(RegistryKeys.BLOCK)
                    .map { wrapper -> wrapper.streamKeys().map { it.value.toString() } }
                    .getOrNull()?.toList() ?: emptyList()
            OneKeyMinerConfig.axe = OneKeyMinerConfig.axe.copy(options = map)
            OneKeyMinerConfig.hoe = OneKeyMinerConfig.hoe.copy(options = map)
            OneKeyMinerConfig.pickaxe = OneKeyMinerConfig.pickaxe.copy(options = map)
            OneKeyMinerConfig.shovel = OneKeyMinerConfig.shovel.copy(options = map)
            OneKeyMinerConfig.shears = OneKeyMinerConfig.shears.copy(options = map)
            OneKeyMinerConfig.any = OneKeyMinerConfig.any.copy(options = map)
        })
    }
}