package cn.enaium.onekeyminer.config

import cn.enaium.mineconf.core.MineConf
import cn.enaium.mineconf.core.MineConfService
import cn.enaium.mineconf.core.gui.render.IdExtension
import cn.enaium.mineconf.core.gui.render.NameKeyProvider
import cn.enaium.mineconf.core.gui.render.Names
import cn.enaium.mineconf.core.gui.render.ScreenExtension
import cn.enaium.mineconf.core.gui.render.itemRender
import cn.enaium.mineconf.core.gui.render.nameRender

/**
 * @author Enaium
 */
class MineConfServiceImpl : MineConfService {
    init {
        // The shapes are named by the keys of the language file of this mod, the game module resolves
        // them through its language.
        Names.registerKeys { value -> (value as? Shape)?.key }
    }

    override fun conf(): MineConf {
        return MineConf("onekeyminer", "OneKeyMiner", OneKeyMinerConfig)
    }

    /**
     * The tool lists hold block ids, so they are drawn with the icon the game shows for them next to
     * the name the game gives them. The shapes have no icon, so they are only named, which also makes
     * the search find a shape by either its name or the constant it is written as.
     */
    override fun extensions(): Collection<ScreenExtension> {
        return listOf(
            IdExtension(listOf("axe", "hoe", "pickaxe", "shovel", "shears", "any"), itemRender()),
            IdExtension(listOf("shape"), nameRender())
        )
    }
}