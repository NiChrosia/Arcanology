package nichrosia.arcanology.type.data.config.ore.impl

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.Material
import net.minecraft.world.gen.feature.OreFeature
import net.minecraft.world.gen.feature.OreFeatureConfig
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.type.id.block.IdentifiedOreBlock
import nichrosia.arcanology.type.id.item.IdentifiedBlockItem
import nichrosia.arcanology.type.data.MaterialHelper
import nichrosia.arcanology.type.data.config.ore.OreConfig
import nichrosia.arcanology.type.world.util.BiomeSelector
import nichrosia.arcanology.type.world.util.ore.Ore
import kotlin.reflect.KProperty

open class NormalOreConfig(
    name: String,
    blockMaterial: Material = Material.STONE,
    oreResistance: Float = 10f,
    oreBlobSize: Int = 1,
    blobsPerChunk: Int = 4,
    range: Pair<Int, Int> = 0 to 50,
    biomeSelector: BiomeSelector = BiomeSelector.Overworld
) : OreConfig<OreFeatureConfig, OreFeature>(name, blockMaterial, oreResistance, oreBlobSize, blobsPerChunk, range, biomeSelector, {
    val ID = Arcanology.idOf(name)

    val normalOre by Registrar.block.propertyOf(ID) { IdentifiedOreBlock(
            FabricBlockSettings.of(blockMaterial)
            .requiresTool()
            .strength(5f, oreResistance)
            .breakByTool(FabricToolTags.PICKAXES, miningLevel), it)
    }

    val normalOreItem by Registrar.item.propertyOf(ID) { IdentifiedBlockItem(normalOre, settings, it) }
    val oreFeature by Registrar.configuredFeature.propertyOf(ID, false) { Registrar.configuredFeature.createOre(it, normalOre, oreBlobSize, blobsPerChunk, range, biomeSelector) }

    Ore(normalOre, normalOreItem, oreFeature)
}) {
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun getValue(materialHelper: MaterialHelper, property: KProperty<*>): Ore<OreFeatureConfig, OreFeature> {
        return content(materialHelper, this).run {
            val block by Registrar.block.propertyOf(ID) { block }
            val item by Registrar.item.propertyOf(ID) { item }

            Ore(block, item, feature)
        }
    }
}