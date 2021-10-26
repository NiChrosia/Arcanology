package nichrosia.registry.config

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import nichrosia.common.config.Config

open class MemberConfig : Config {
    var createBlockItem: Boolean = true
    var blockItemSettings: FabricItemSettings = FabricItemSettings()
}