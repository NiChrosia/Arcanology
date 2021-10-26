package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricMaterialBuilder
import net.minecraft.block.MapColor
import net.minecraft.block.Material
import nichrosia.arcanology.Arcanology
import nichrosia.registry.BasicRegistrar

open class BlockMaterialRegistrar : BasicRegistrar<Material>() {
    val velosium by memberOf(Arcanology.identify("velosium")) { create(MapColor.MAGENTA) }
    val xenothite by memberOf(Arcanology.identify("xenothite")) { create(MapColor.TEAL) }
    val elementalCrystal by memberOf(Arcanology.identify("elemental_crystal")) { create(MapColor.MAGENTA) }
    val aluminum by memberOf(Arcanology.identify("aluminum")) { create(MapColor.LIGHT_GRAY) }
    val silver by memberOf(Arcanology.identify("silver")) { create(MapColor.LIGHT_GRAY) }

    fun create(color: MapColor): Material {
        return FabricMaterialBuilder(color).build()
    }
}