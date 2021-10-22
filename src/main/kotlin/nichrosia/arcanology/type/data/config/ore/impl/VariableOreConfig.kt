package nichrosia.arcanology.type.data.config.ore.impl

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.Material
import net.minecraft.world.gen.feature.util.FeatureContext
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.type.data.MaterialHelper
import nichrosia.arcanology.type.data.config.ore.OreConfig
import nichrosia.arcanology.type.id.block.IdentifiedOreBlock
import nichrosia.arcanology.type.id.item.IdentifiedBlockItem
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
    val variableOre by Registrar.block.propertyOf(name) {
        IdentifiedOreBlock(FabricBlockSettings.of(blockMaterial)
            .requiresTool()
            .strength(5f, oreResistance)
            .breakByTool(FabricToolTags.PICKAXES, miningLevel), it)
    }

    val variableOreItem by Registrar.item.propertyOf(name) { IdentifiedBlockItem(variableOre, settings, it) }

    val variableOreFeature by Registrar.configuredFeature.propertyOf(name, false) { Registrar.configuredFeature.createVariableOre(oreID, variableOre, range, blobsPerChunk, biomeSelector, sizeFactory) }

    Ore(variableOre, variableOreItem, variableOreFeature)
}) {
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun getValue(materialHelper: MaterialHelper, property: KProperty<*>): Ore<CustomOreFeatureConfig, CustomOreFeature> {
        return content(materialHelper, this).run {
            val block by Registrar.block.propertyOf(ID) { block }
            val item by Registrar.item.propertyOf(ID) { item }

            Ore(block, item, feature)
        }
    }
}