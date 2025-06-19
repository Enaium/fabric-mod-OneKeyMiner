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
package cn.enaium.onekeyminer.callback.impl

import cn.enaium.onekeyminer.Config
import cn.enaium.onekeyminer.callback.UseOnBlockCallback
import cn.enaium.onekeyminer.util.findBlocks
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.Direction
import net.minecraft.world.World

/**
 * @author Enaium
 */
abstract class UseOnBlockCallbackImpl : UseOnBlockCallback {
    override fun interact(
        player: ServerPlayerEntity,
        world: World,
        stack: ItemStack,
        hand: Hand,
        hitResult: BlockHitResult
    ) {
        val canMine =
            stack.item.canMine(stack, world.getBlockState(hitResult.blockPos), world, hitResult.blockPos, player)
        val config = Config.model
        if (canMine && config.interact && condition(player)) {
            for (block in findBlocks(world, hitResult.blockPos, config.limit)) {
                stack.item.useOnBlock(
                    ItemUsageContext(
                        player,
                        hand,
                        BlockHitResult(block.toCenterPos(), Direction.UP, block, false)
                    )
                )
            }
        }
    }

    abstract fun condition(player: ServerPlayerEntity): Boolean
}
