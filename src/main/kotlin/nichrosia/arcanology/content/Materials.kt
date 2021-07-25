package nichrosia.arcanology.content

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricMaterialBuilder
import net.minecraft.block.MapColor
import net.minecraft.block.Material

open class Materials : Loadable {
    override fun load() {
        velosium = FabricMaterialBuilder(MapColor.MAGENTA).build()
        naturalVelosium = FabricMaterialBuilder(MapColor.PURPLE).build()

        elementalCrystal = FabricMaterialBuilder(MapColor.MAGENTA).build()
    }

    companion object {
        lateinit var velosium: Material
        lateinit var naturalVelosium: Material

        lateinit var elementalCrystal: Material
    }
}