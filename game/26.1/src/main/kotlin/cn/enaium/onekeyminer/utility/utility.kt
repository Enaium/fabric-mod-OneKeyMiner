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
import net.minecraft.core.BlockPos
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.server.level.ServerPlayer
import net.minecraft.tags.ItemTags
import net.minecraft.world.item.ShearsItem
import net.minecraft.world.level.Level

/**
 * @author Enaium
 */
fun Level.getBlockName(blockPos: BlockPos): String {
    return BuiltInRegistries.BLOCK.getKey(this.getBlockState(blockPos).block).toString()
}

fun BlockPos.toCommon(level: Level): cn.enaium.onekeyminer.common.BlockPos {
    return object : cn.enaium.onekeyminer.common.BlockPos(x, y, z) {
        override val name: String
            get() = level.getBlockName(this@toCommon)

        override fun add(x: Int, y: Int, z: Int): cn.enaium.onekeyminer.common.BlockPos {
            return this@toCommon.offset(x, y, z).toCommon(level)
        }
    }
}

fun ServerPlayer.toCommon(world: Level): Player {
    return object : Player {
        override val sneaking: Boolean
            get() = this@toCommon.isShiftKeyDown

        override val activating: Boolean
            get() = activeKeyBind.isDown

        override val handTool: Tool
            get() = this@toCommon.inventory.getSlot(this@toCommon.inventory.selectedSlot)?.get()?.let { handStack ->
                if (handStack.tags().anyMatch { it == ItemTags.AXES }) {
                    Tool.AXE
                } else if (handStack.tags().anyMatch { it == ItemTags.HOES }) {
                    Tool.HOE
                } else if (handStack.tags().anyMatch { it == ItemTags.PICKAXES }) {
                    Tool.PICKAXE
                } else if (handStack.tags().anyMatch { it == ItemTags.SHOVELS }) {
                    Tool.SHOVEL
                } else if (handStack.item is ShearsItem) {
                    Tool.SHEARS
                } else {
                    Tool.ANY
                }
            } ?: Tool.ANY

        override fun canMine(pos: cn.enaium.onekeyminer.common.BlockPos): Boolean {
            val pos1 = BlockPos(pos.x, pos.y, pos.z)
            return this@toCommon.inventory.getSlot(this@toCommon.inventory.selectedSlot)?.get()?.let { handStack ->
                handStack.item.canDestroyBlock(
                    handStack,
                    world.getBlockState(pos1),
                    world,
                    pos1,
                    this@toCommon
                )
            } ?: false
        }

        override val mainHandEmpty: Boolean
            get() = this@toCommon.inventory.selectedItem.isEmpty
    }
}