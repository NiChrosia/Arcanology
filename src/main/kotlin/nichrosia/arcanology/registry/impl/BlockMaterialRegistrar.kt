package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricMaterialBuilder
import net.minecraft.block.MapColor
import net.minecraft.block.Material
import net.minecraft.util.Identifier
import nichrosia.arcanology.registry.BasicRegistrar
import nichrosia.arcanology.registry.properties.RegistrarProperty

open class BlockMaterialRegistrar : BasicRegistrar<Material>() {
    val velosium by RegistrarProperty("velosium") { create(it, MapColor.MAGENTA) }
    val xenothite by RegistrarProperty("xenothite") { create(it, MapColor.TEAL) }
    val elementalCrystal by RegistrarProperty("elemental_crystal") { create(it, MapColor.MAGENTA) }
    val aluminum by RegistrarProperty("aluminum") { create(it, MapColor.LIGHT_GRAY) }
    val silver by RegistrarProperty("silver") { create(it, MapColor.LIGHT_GRAY) }

    fun create(ID: Identifier, color: MapColor): Material {
        return super.create(ID, FabricMaterialBuilder(color).build())
    }
}