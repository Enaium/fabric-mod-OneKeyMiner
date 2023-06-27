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
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.HoeItem
import net.minecraft.item.ItemStack
import net.minecraft.item.ToolItem
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World

/**
 * @author Enaium
 */
class UseOnBlockCallbackImpl : UseOnBlockCallback {
    override fun interact(
        stack: ItemStack?,
        player: PlayerEntity,
        world: World,
        blockPos: BlockPos,
        direction: Direction
    ) {
        stack ?: return
        val config = Config.model
        if (config.interact && (stack.item is ToolItem || stack.item is HoeItem) && player.isSneaking) {
            for (block in findBlocks(world, blockPos, config.limit)) {
                stack.item.use(stack, player, world, block, direction, 0f, 0f, 0f)
            }
        }
    }
}
