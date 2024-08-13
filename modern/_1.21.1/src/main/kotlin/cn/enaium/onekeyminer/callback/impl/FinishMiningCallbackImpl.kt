package cn.enaium.onekeyminer.callback.impl

import cn.enaium.onekeyminer.Config
import cn.enaium.onekeyminer.callback.FinishMiningCallback
import cn.enaium.onekeyminer.util.findBlocks
import cn.enaium.onekeyminer.util.getName
import net.minecraft.item.*
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
            val canMine = stack.item.canMine(world.getBlockState(pos), world, pos, player)
            if (canMine && (stack.item is MiningToolItem || stack.item is ShearsItem) && condition(player)) {
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

                    is MiningToolItem -> {
                        list.addAll(config.any)
                    }
                }
                val name = getName(world, pos)
                if (list.contains(name)) {
                    findBlocks(world, pos, config.limit).forEach {
                        if (player.inventory.mainHandStack.isEmpty) {
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