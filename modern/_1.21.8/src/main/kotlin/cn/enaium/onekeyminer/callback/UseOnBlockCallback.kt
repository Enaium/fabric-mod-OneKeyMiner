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
package cn.enaium.onekeyminer.callback

import net.fabricmc.fabric.api.event.EventFactory
import net.minecraft.item.ItemStack
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.world.World

/**
 * @author Enaium
 */
fun interface UseOnBlockCallback {
    fun interact(player: ServerPlayerEntity, world: World, stack: ItemStack, hand: Hand, hitResult: BlockHitResult)

    companion object {
        val EVENT = EventFactory.createArrayBacked(
            UseOnBlockCallback::class.java
        ) { listeners: Array<UseOnBlockCallback> ->
            UseOnBlockCallback { player: ServerPlayerEntity, world: World, stack: ItemStack, hand: Hand, hitResult: BlockHitResult ->
                for (listener in listeners) {
                    listener.interact(player, world, stack, hand, hitResult)
                }
            }
        }
    }
}
