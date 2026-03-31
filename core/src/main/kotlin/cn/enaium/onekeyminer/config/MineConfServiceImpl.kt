package cn.enaium.onekeyminer.config

import cn.enaium.mineconf.core.MineConf
import cn.enaium.mineconf.core.MineConfService

/**
 * @author Enaium
 */
class MineConfServiceImpl : MineConfService {
    override fun conf(): MineConf {
        return MineConf("onekeyminer", "OneKeyMiner", OneKeyMinerConfig)
    }
}