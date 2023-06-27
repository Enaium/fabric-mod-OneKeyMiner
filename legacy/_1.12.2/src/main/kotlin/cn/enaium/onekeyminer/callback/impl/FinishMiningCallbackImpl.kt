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
import cn.enaium.onekeyminer.callback.FinishMiningCallback
import cn.enaium.onekeyminer.util.findBlocks
import cn.enaium.onekeyminer.util.getName
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.*
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
 * @author Enaium
 */
class FinishMiningCallbackImpl : FinishMiningCallback {
    override fun interact(
        world: World,
        player: PlayerEntity,
        pos: BlockPos,
        tryBreak: (BlockPos) -> Unit
    ): ActionResult {
        val stack = player.inventory.getInvStack(player.inventory.selectedSlot)
        if (stack != null) {
            if ((stack.item is ToolItem || stack.item is HoeItem || stack.item is ShearsItem) && player.isSneaking) {
                val config = Config.model
                val list: MutableList<String> = ArrayList()
                when (stack.item) {
                    is AxeItem -> {
                        list.addAll(config.axe)
                    }

                    is HoeItem -> {
                        list.addAll(config.hoe)
                    }

                    is PickaxeItem -> {
                        list.addAll(config.pickaxe)
                    }

                    is ShovelItem -> {
                        list.addAll(config.shovel)
                    }

                    is ShearsItem -> {
                        list.addAll(config.shears)
                    }

                    is ToolItem -> {
                        list.addAll(config.any)
                    }
                }
                val name = getName(world, pos)
                if (list.contains(name)) {
                    findBlocks(world, pos, config.limit).forEach { tryBreak(it) }
                }
            }
        }
        return ActionResult.SUCCESS
    }
}
