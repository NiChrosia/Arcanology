@file:Suppress("deprecation")

package nichrosia.arcanology.content

import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.OreBlock
import net.minecraft.structure.rule.BlockStateMatchRuleTest
import net.minecraft.util.Identifier
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.feature.*
import net.minecraft.world.gen.feature.util.FeatureContext
import nichrosia.arcanology.type.world.feature.CustomOreFeature
import nichrosia.arcanology.type.world.feature.CustomOreFeatureConfig
import nichrosia.arcanology.type.world.util.BiomeSelector
import nichrosia.arcanology.util.range

@Suppress("UNCHECKED_CAST")
object ConfiguredFeatureRegistrar {
    fun register(id: Identifier, value: ConfiguredFeature<*, *>): ConfiguredFeature<*, *> {
        return Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, id, value)
    }

    /** Register a normal ore in an arbitrary dimension. */
    fun registerOre(
        /** The ID of the ore feature. */
        ID: Identifier,
        /** The ore to be generated. */
        ore: OreBlock,
        /** The amount of ore blocks in a ore blob. */
        oreBlobSize: Int,
        /** The number of ore blobs generated in a chunk. */
        blobsPerChunk: Int,
        /** The vertical range that this ore generates in. The first is minimum, second maximum. */
        range: Pair<Int, Int>,
        /** The biome selector used to determine where to generate the ore. */
        biomeSelector: BiomeSelector,
        /** The block that should be replaced by the ore. */
        replaceable: Block = when(biomeSelector) {
            BiomeSelector.Overworld -> Blocks.STONE
            BiomeSelector.TheNether -> Blocks.NETHERRACK
            BiomeSelector.TheEnd -> Blocks.END_STONE
        }
    ): ConfiguredFeature<OreFeatureConfig, OreFeature> {
        val key = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, ID)

        val feature = register(ID, Feature.ORE.configure(OreFeatureConfig(
            BlockStateMatchRuleTest(replaceable.defaultState), ore.defaultState, oreBlobSize
        )).range(range).repeat(blobsPerChunk).spreadHorizontally())

        BiomeModifications.addFeature(biomeSelector.environment, GenerationStep.Feature.UNDERGROUND_ORES, key)

        return feature as ConfiguredFeature<OreFeatureConfig, OreFeature>
    }

    /** Register a normal & deepslate overworld ore. */
    fun registerNormalAndDeepslateOre(
        /** The ID of the ore feature. */
        ID: Identifier,
        /** The non-deepslate ore. */
        ore: OreBlock,
        /** The deepslate ore variant. */
        deepslateOre: OreBlock,
        /** The amount of ore blocks in a ore blob. */
        oreBlobSize: Int,
        /** The number of ore blobs generated in a chunk. */
        blobsPerChunk: Int,
        /** The vertical range that this ore generates in. The first is minimum, second maximum. */
        range: Pair<Int, Int>
    ): ConfiguredFeature<OreFeatureConfig, OreFeature> {
        val key = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, ID)

        val feature = register(key.value, Feature.ORE.configure(OreFeatureConfig(
            listOf(
                OreFeatureConfig.createTarget(OreFeatureConfig.Rules.STONE_ORE_REPLACEABLES, ore.defaultState),
                OreFeatureConfig.createTarget(OreFeatureConfig.Rules.DEEPSLATE_ORE_REPLACEABLES, deepslateOre.defaultState)
            ), oreBlobSize
        )).range(range).repeat(blobsPerChunk).spreadHorizontally())

        BiomeModifications.addFeature(BiomeSelector.Overworld.environment, GenerationStep.Feature.UNDERGROUND_ORES, key)

        return feature as ConfiguredFeature<OreFeatureConfig, OreFeature>
    }

    /** Register an ore with a variable size. */
    fun registerVariableOre(
        /** The ID of the ore feature. */
        ID: Identifier,
        /** The ore to be generated. */
        ore: OreBlock,
        /** The vertical range that this ore generates in. The first is minimum, second maximum. */
        range: Pair<Int, Int>,
        /** The number of ore blobs generated in a chunk. */
        blobsPerChunk: Int,
        /** The biome selector used to determine where to generate the ore. */
        biomeSelector: BiomeSelector,
        /** The factory for the custom variable size. */
        sizeFactory: (FeatureContext<CustomOreFeatureConfig>) -> Int = { fallbackSize },
        /** The block that should be replaced by the ore. */
        replaceable: Block = when(biomeSelector) {
            BiomeSelector.Overworld -> Blocks.STONE
            BiomeSelector.TheNether -> Blocks.NETHERRACK
            BiomeSelector.TheEnd -> Blocks.END_STONE
        },
        /** The fallback size to be used, if either the sizeFactory is not overridden, or for some reason it cannot be used. */
        fallbackSize: Int = 1
    ): ConfiguredFeature<CustomOreFeatureConfig, CustomOreFeature> {
        val key = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, ID)

        val feature = register(ID, CustomOreFeature.instance.configure(CustomOreFeatureConfig(
            BlockStateMatchRuleTest(replaceable.defaultState), ore.defaultState, fallbackSize, 1f, sizeFactory
        )).range(range).repeat(blobsPerChunk).spreadHorizontally())

        BiomeModifications.addFeature(biomeSelector.environment, GenerationStep.Feature.UNDERGROUND_ORES, key)

        return feature as ConfiguredFeature<CustomOreFeatureConfig, CustomOreFeature>
    }
}