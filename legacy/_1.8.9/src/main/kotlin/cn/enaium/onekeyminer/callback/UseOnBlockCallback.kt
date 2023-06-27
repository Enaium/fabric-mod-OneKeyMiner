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

import net.legacyfabric.fabric.api.event.EventFactory
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

/**
 * @author Enaium
 */
fun interface UseOnBlockCallback {
    fun interact(
        stack: ItemStack?,
        player: PlayerEntity,
        world: World,
        blockPos: BlockPos,
        direction: Direction
    )

    companion object {
        val EVENT = EventFactory.createArrayBacked(
            UseOnBlockCallback::class.java
        ) { listeners: Array<UseOnBlockCallback> ->
            UseOnBlockCallback { stack: ItemStack?, player: PlayerEntity, world: World, blockPos: BlockPos, direction: Direction ->
                for (listener in listeners) {
                    listener.interact(stack, player, world, blockPos, direction)
                }
            }
        }
    }
}
