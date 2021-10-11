package nichrosia.arcanology.type.data.config.ore.impl

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.Material
import net.minecraft.block.OreBlock
import net.minecraft.item.BlockItem
import net.minecraft.world.gen.feature.util.FeatureContext
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.registry.properties.RegistrarProperty
import nichrosia.arcanology.type.data.MaterialHelper
import nichrosia.arcanology.type.data.config.ore.OreConfig
import nichrosia.arcanology.type.world.feature.CustomOreFeature
import nichrosia.arcanology.type.world.feature.CustomOreFeatureConfig
import nichrosia.arcanology.type.world.util.BiomeSelector
import nichrosia.arcanology.type.world.util.ore.Ore
import kotlin.reflect.KProperty

open class VariableOreConfig(
    name: String,
    blockMaterial: Material = Material.STONE,
    oreResistance: Float = 100f,
    oreBlobSize: Int = 8,
    blobsPerChunk: Int = 4,
    range: Pair<Int, Int> = 0 to 50,
    biomeSelector: BiomeSelector = BiomeSelector.Overworld,
    val sizeFactory: (FeatureContext<CustomOreFeatureConfig>) -> Int = { blobsPerChunk }
) : OreConfig<CustomOreFeatureConfig, CustomOreFeature>(name, blockMaterial, oreResistance, oreBlobSize, blobsPerChunk, range, biomeSelector, {
    val oreID = Arcanology.idOf(name)
    val variableOre by RegistrarProperty(Registrar.block, name) {
        OreBlock(FabricBlockSettings.of(blockMaterial)
            .requiresTool()
            .strength(5f, oreResistance)
            .breakByTool(FabricToolTags.PICKAXES, miningLevel))
    }

    val variableOreItem by RegistrarProperty(Registrar.item, name) { BlockItem(variableOre, settings) }

    val variableOreFeature by RegistrarProperty(Registrar.configuredFeature, name, false) { Registrar.configuredFeature.createVariableOre(oreID, variableOre, range, blobsPerChunk, biomeSelector, sizeFactory) }

    Ore(variableOre, variableOreItem, variableOreFeature)
}) {
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun getValue(materialHelper: MaterialHelper, property: KProperty<*>): Ore<CustomOreFeatureConfig, CustomOreFeature> {
        return content(materialHelper, this).run {
            val block by RegistrarProperty(Registrar.block, name) { block }
            val item by RegistrarProperty(Registrar.item, name) { item }

            Ore(block, item, feature)
        }
    }
}