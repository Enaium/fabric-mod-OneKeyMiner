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

package cn.enaium.onekeyminer.command

import cn.enaium.onekeyminer.Config
import cn.enaium.onekeyminer.enums.Action
import cn.enaium.onekeyminer.enums.Tool
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.command.AbstractCommand
import net.minecraft.command.CommandSource
import net.minecraft.command.NotFoundException
import net.minecraft.text.*
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import kotlin.random.Random
import kotlin.random.nextInt

class OneKeyMinerCommand : AbstractCommand() {
    override fun getCommandName(): String {
        return "onekeyminer"
    }

    override fun getUsageTranslationKey(source: CommandSource): String? {
        return null
    }

    override fun execute(
        commandSource: CommandSource,
        args: Array<out String>
    ) {
        if (args.isEmpty()) {
            commandSource.sendMessage(LiteralText("§cOneKeyMiner §7by §bEnaium"))
            return
        }

        if (args[0].equals("reload", ignoreCase = true)) {
            Config.load()
            commandSource.sendMessage(TranslatableText("command.reload.success"))
            return
        }

        if (args[0].equals("limit", ignoreCase = true)) {
            if (args.size == 1) {
                commandSource.sendMessage(TranslatableText("command.limit.get", Config.model.limit))
            } else {
                Config.model.limit = args[1].toInt()
                commandSource.sendMessage(TranslatableText("command.limit.success", Config.model.limit))
            }
            return
        }

        if (args[0].equals("interact", ignoreCase = true)) {
            if (args.size == 1) {
                commandSource.sendMessage(TranslatableText("command.interact.get", Config.model.interact))
            } else {
                Config.model.interact = args[1].toBoolean()
                commandSource.sendMessage(TranslatableText("command.interact.success", Config.model.interact))
            }
            return
        }

        if (args.size <= 3) {
            val list = when (Tool.valueOf(args[0])) {
                Tool.AXE -> Config.model.axe
                Tool.HOE -> Config.model.hoe
                Tool.PICKAXE -> Config.model.pickaxe
                Tool.SHOVEL -> Config.model.shovel
                Tool.SHEARS -> Config.model.shears
                Tool.ANY -> Config.model.any
            }

            if (args[1].equals("list", ignoreCase = true)) {
                var previous: Text? = null
                for (name in list) {
                    val block = LiteralText(name).setStyle(
                        Style().setHoverEvent(
                            HoverEvent(
                                HoverEvent.Action.SHOW_TEXT,
                                TranslatableText(Block.REGISTRY[Identifier(name)].translatedName)
                            )
                        )
                    )
                    if (previous == null) {
                        previous = block
                    } else {
                        previous.append(LiteralText(", "))
                        previous.append(block)
                    }
                }
                if (previous != null) {
                    commandSource.sendMessage(previous)
                }
                return
            }

            if (args.size != 3) {
                throw NotFoundException()
            }

            val blockName = args[2].let {
                if (Block.REGISTRY.get(Identifier(it)) == Blocks.AIR) {
                    commandSource.sendMessage(TranslatableText("command.action.notFound", it))
                    return
                } else {
                    it
                }
            }

            when (Action.valueOf(args[1])) {
                Action.ADD -> {
                    list.add(blockName)
                    commandSource.sendMessage(TranslatableText("command.action.add.success", blockName))
                }

                Action.REMOVE -> {
                    list.remove(blockName)
                    commandSource.sendMessage(TranslatableText("command.action.remove.success", blockName))
                }
            }
        }
    }

    override fun getAutoCompleteHints(
        source: CommandSource,
        strings: Array<String>,
        pos: BlockPos?
    ): List<String> {
        if (strings.size == 1) {
            return listOf("reload", "limit", "interact") + Tool.entries.map { it.name }
        }
        if (strings.size == 2) {
            return when (strings[0]) {
                "limit" -> listOf(Random.nextInt(1..64).toString())
                "interact" -> listOf("true", "false")
                else -> listOf("list") + Action.entries.map { it.name }
            }
        }
        if (strings.size == 3) {
            if (Tool.entries.map { it.name }.contains(strings[0]) && Action.entries.map { it.name }
                    .contains(strings[1])) {
                return Block.REGISTRY.keySet().map { it.toString() }
            }
        }
        return emptyList()
    }
}