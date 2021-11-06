package nichrosia.arcanology.type.content.block.settings

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Material

open class ArcanologyBlockSettings(material: Material, open val miningLevel: Int) : FabricBlockSettings(material, material.color) {
}