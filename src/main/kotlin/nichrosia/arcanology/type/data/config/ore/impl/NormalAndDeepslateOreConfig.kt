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
import nichrosia.arcanology.type.world.util.ore.NormalAndDeepslateOre
import kotlin.reflect.KProperty

open class NormalAndDeepslateOreConfig(
    name: String,
    blockMaterial: Material = Material.STONE,
    oreResistance: Float = 100f,
    oreBlobSize: Int = 8,
    blobsPerChunk: Int = 4,
    range: Pair<Int, Int> = 0 to 50,
) : OreConfig<OreFeatureConfig, OreFeature>(name, blockMaterial, oreResistance, oreBlobSize, blobsPerChunk, range, BiomeSelector.Overworld, {
    val oreID = Arcanology.idOf(name)
    val normalOre by Registrar.block.propertyOf(name) { IdentifiedOreBlock(
            FabricBlockSettings.of(blockMaterial)
            .requiresTool()
            .strength(5f, oreResistance)
            .breakByTool(FabricToolTags.PICKAXES, miningLevel), it)
    }

    val normalOreItem by Registrar.item.propertyOf(name) { IdentifiedBlockItem(normalOre, settings, it) }

    val deepslateOre by Registrar.block.propertyOf("deepslate_${name}") {
        IdentifiedOreBlock(FabricBlockSettings.of(blockMaterial)
            .requiresTool()
            .strength(5f, oreResistance * 2f)
            .breakByTool(FabricToolTags.PICKAXES, miningLevel), it)
    }

    val deepslateOreItem by Registrar.item.propertyOf("deepslate_${name}") { IdentifiedBlockItem(deepslateOre, settings, it) }

    val normalAndDeepslateOreFeature by Registrar.configuredFeature.propertyOf(name, false) { Registrar.configuredFeature.createNormalAndDeepslateOre(oreID, normalOre, deepslateOre, oreBlobSize, blobsPerChunk, range) }

    NormalAndDeepslateOre(normalOre, normalOreItem, deepslateOre, deepslateOreItem, normalAndDeepslateOreFeature)
}) {
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun getValue(materialHelper: MaterialHelper, property: KProperty<*>): NormalAndDeepslateOre {
        return (content(materialHelper, this) as NormalAndDeepslateOre).run {
            val block by Registrar.block.propertyOf(ID) { block }
            val item by Registrar.item.propertyOf(ID) { item }
            val deepslateBlock by Registrar.block.propertyOf("deepslate_${ID}") { deepslateBlock }
            val deepslateItem by Registrar.item.propertyOf("deepslate_${ID}") { deepslateItem }

            NormalAndDeepslateOre(block, item, deepslateBlock, deepslateItem, feature)
        }
    }
}