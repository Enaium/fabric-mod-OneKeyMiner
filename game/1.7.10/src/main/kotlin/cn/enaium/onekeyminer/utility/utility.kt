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
package cn.enaium.onekeyminer.utility

import cn.enaium.onekeyminer.KeyBinds.activeKeyBind
import cn.enaium.onekeyminer.common.Player
import cn.enaium.onekeyminer.common.Tool
import net.minecraft.block.Block
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.*
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/**
 * @author Enaium
 */
fun World.getBlockName(blockPos: BlockPos): String {
    return Block.REGISTRY.getId(this.getBlock(blockPos.x, blockPos.y, blockPos.z))
}

fun BlockPos.toCommon(world: World): cn.enaium.onekeyminer.common.BlockPos {
    return object : cn.enaium.onekeyminer.common.BlockPos(x, y, z) {
        override val name: String
            get() = world.getBlockName(this@toCommon)

        override fun add(x: Int, y: Int, z: Int): cn.enaium.onekeyminer.common.BlockPos {
            return BlockPos(this.x + x, this.y + y, this.z + z).toCommon(world)
        }
    }
}

fun PlayerEntity.toCommon(world: World): Player {
    return object : Player {
        override val sneaking: Boolean
            get() = this@toCommon.isSneaking

        override val activating: Boolean
            get() = activeKeyBind.isPressed

        override val handTool: Tool
            get() = this@toCommon.inventory.getInvStack(this@toCommon.inventory.selectedSlot)?.let { handStack ->
                when (handStack.item) {
                    is AxeItem -> {
                        Tool.AXE
                    }

                    is HoeItem -> {
                        Tool.HOE
                    }

                    is PickaxeItem -> {
                        Tool.PICKAXE
                    }

                    is ShovelItem -> {
                        Tool.SHOVEL
                    }

                    is ShearsItem -> {
                        Tool.SHEARS
                    }

                    else -> {
                        Tool.ANY
                    }
                }
            } ?: Tool.ANY

        override fun canMine(pos: cn.enaium.onekeyminer.common.BlockPos): Boolean {
            return true
        }

        override val mainHandEmpty: Boolean
            get() = this@toCommon.inventory.mainHandStack == null
    }
}