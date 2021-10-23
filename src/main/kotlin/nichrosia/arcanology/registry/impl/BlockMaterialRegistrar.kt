package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricMaterialBuilder
import net.minecraft.block.MapColor
import net.minecraft.block.Material
import nichrosia.arcanology.Arcanology
import nichrosia.common.identity.ID
import nichrosia.registry.BasicRegistrar

open class BlockMaterialRegistrar : BasicRegistrar<Material>() {
    val velosium by memberOf(ID(Arcanology.modID, "velosium")) { create(MapColor.MAGENTA) }
    val xenothite by memberOf(ID(Arcanology.modID, "xenothite")) { create(MapColor.TEAL) }
    val elementalCrystal by memberOf(ID(Arcanology.modID, "elemental_crystal")) { create(MapColor.MAGENTA) }
    val aluminum by memberOf(ID(Arcanology.modID, "aluminum")) { create(MapColor.LIGHT_GRAY) }
    val silver by memberOf(ID(Arcanology.modID, "silver")) { create(MapColor.LIGHT_GRAY) }

    fun create(color: MapColor): Material {
        return FabricMaterialBuilder(color).build()
    }
}