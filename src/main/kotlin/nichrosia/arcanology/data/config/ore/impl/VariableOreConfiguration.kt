package nichrosia.arcanology.data.config.ore.impl

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.Material
import net.minecraft.block.OreBlock
import net.minecraft.item.BlockItem
import net.minecraft.world.gen.feature.util.FeatureContext
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.content.ConfiguredFeatureRegistrar
import nichrosia.arcanology.data.MaterialHelper
import nichrosia.arcanology.data.config.ore.OreConfiguration
import nichrosia.arcanology.data.ore.Ore
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.registry.properties.RegistrarProperty
import nichrosia.arcanology.type.world.feature.CustomOreFeature
import nichrosia.arcanology.type.world.feature.CustomOreFeatureConfig
import nichrosia.arcanology.type.world.util.BiomeSelector
import kotlin.reflect.KProperty

open class VariableOreConfiguration(
    name: String,
    blockMaterial: Material = Material.STONE,
    oreResistance: Float = 100f,
    oreBlobSize: Int = 8,
    blobsPerChunk: Int = 4,
    range: Pair<Int, Int> = 0 to 50,
    biomeSelector: BiomeSelector = BiomeSelector.Overworld,
    val sizeFactory: (FeatureContext<CustomOreFeatureConfig>) -> Int = { blobsPerChunk }
) : OreConfiguration<CustomOreFeatureConfig, CustomOreFeature>(name, blockMaterial, oreResistance, oreBlobSize, blobsPerChunk, range, biomeSelector, {
    val oreID = Arcanology.idOf(name)
    val variableOre by RegistrarProperty(Registrar.block, name) {
        OreBlock(FabricBlockSettings.of(blockMaterial)
            .requiresTool()
            .strength(5f, oreResistance)
            .breakByTool(FabricToolTags.PICKAXES, miningLevel))
    }

    val variableOreItem by RegistrarProperty(Registrar.item, name) { BlockItem(variableOre, settings) }

    val variableOreFeature = ConfiguredFeatureRegistrar.registerVariableOre(oreID, variableOre, range, blobsPerChunk, biomeSelector, sizeFactory)

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