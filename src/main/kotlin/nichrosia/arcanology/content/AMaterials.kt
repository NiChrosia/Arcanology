package nichrosia.arcanology.content

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricMaterialBuilder
import net.minecraft.block.MapColor
import net.minecraft.block.Material

object AMaterials : Content() {
    lateinit var velosium: Material
    lateinit var xenothite: Material
    lateinit var elementalCrystal: Material

    override fun load() {
        velosium = FabricMaterialBuilder(MapColor.MAGENTA).build()

        xenothite = FabricMaterialBuilder(MapColor.TEAL).build()

        elementalCrystal = FabricMaterialBuilder(MapColor.MAGENTA).build()
    }

}