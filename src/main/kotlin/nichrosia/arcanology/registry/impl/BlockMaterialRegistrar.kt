package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricMaterialBuilder
import net.minecraft.block.MapColor
import net.minecraft.block.Material
import nichrosia.arcanology.registry.BasicRegistrar
import nichrosia.arcanology.registry.properties.RegistryProperty

open class BlockMaterialRegistrar : BasicRegistrar<Material>() {
    val velosium by RegistryProperty("velosium") { create(it, MapColor.MAGENTA) }
    val xenothite by RegistryProperty("xenothite") { create(it, MapColor.TEAL) }
    val elementalCrystal by RegistryProperty("elemental_crystal") { create(it, MapColor.MAGENTA) }
    val aluminum by RegistryProperty("aluminum") { create(it, MapColor.LIGHT_GRAY) }
    val silver by RegistryProperty("silver") { create(it, MapColor.LIGHT_GRAY) }

    fun create(name: String, color: MapColor): Material {
        return super.create(name, FabricMaterialBuilder(color).build())
    }
}