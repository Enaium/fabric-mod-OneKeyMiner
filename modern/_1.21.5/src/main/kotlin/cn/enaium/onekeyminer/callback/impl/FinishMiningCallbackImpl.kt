package cn.enaium.onekeyminer.callback.impl

import cn.enaium.onekeyminer.Config
import cn.enaium.onekeyminer.callback.FinishMiningCallback
import cn.enaium.onekeyminer.util.findBlocks
import cn.enaium.onekeyminer.util.getName
import net.minecraft.item.ShearsItem
import net.minecraft.registry.tag.ItemTags
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.ActionResult
import net.minecraft.util.math.BlockPos

/**
 * @author Enaium
 */
abstract class FinishMiningCallbackImpl : FinishMiningCallback {
    override fun interact(
        world: ServerWorld,
        player: ServerPlayerEntity,
        pos: BlockPos,
        tryBreak: (BlockPos) -> Unit
    ): ActionResult {
        val stack = player.inventory.getStack(player.inventory.selectedSlot)
        if (stack != null) {
            val canMine = stack.item.canMine(stack, world.getBlockState(pos), world, pos, player)
            if (canMine && condition(player)) {
                val config = Config.model
                val list: MutableList<String> = ArrayList()
                if (stack.streamTags().anyMatch { it == ItemTags.AXES }) {
                    list.addAll(config.axe)
                } else if (stack.streamTags().anyMatch { it == ItemTags.HOES }) {
                    list.addAll(config.hoe)
                } else if (stack.streamTags().anyMatch { it == ItemTags.PICKAXES }) {
                    list.addAll(config.pickaxe)
                } else if (stack.streamTags().anyMatch { it == ItemTags.SHOVELS }) {
                    list.addAll(config.shovel)
                }

                when (stack.item) {
                    is ShearsItem -> {
                        list.addAll(config.shears)
                    }
                }
                val name = getName(world, pos)
                if (list.contains(name)) {
                    findBlocks(world, pos, config.limit).forEach {
                        if (player.inventory.mainStacks.isEmpty()) {
                            return@forEach
                        }
                        tryBreak(it)
                    }
                }
            }
        }
        return ActionResult.SUCCESS
    }

    abstract fun condition(player: ServerPlayerEntity): Boolean
}