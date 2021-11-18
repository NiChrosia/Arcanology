@file:Suppress("deprecation")

package arcanology.registrar.impl

import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.world.gen.feature.ConfiguredFeature
import arcanology.type.common.registar.minecraft.VanillaRegistrar

@Suppress("UNCHECKED_CAST")
open class ConfiguredFeatureContentRegistrar : VanillaRegistrar.Basic<ConfiguredFeature<*, *>>(BuiltinRegistries.CONFIGURED_FEATURE) {
//    open val languageGenerator: LanguageGenerator = BasicLanguageGenerator()
//
//    open val metadataRegistry: MutableMap<ID, FeatureMetadata> = mutableMapOf()
//
//    override fun <E : ConfiguredFeature<*, *>> register(location: ID, value: E): E {
//        super.register(location, value)
//
//        val metadata = metadataRegistry[location] ?: throw IllegalStateException("Attempted to find metadata in registry, but it was not created correctly.")
//
//        return registerOre(location, value, metadata.biomeSelector, metadata.generationStep) as E
//    }
//
//    open fun registerOre(
//        key: RegistryKey<ConfiguredFeature<*, *>>,
//        value: ConfiguredFeature<*, *>,
//        biomeSelector: BiomeSelector = BiomeSelector.Overworld,
//        generationStep: GenerationStep.Feature = GenerationStep.Feature.UNDERGROUND_ORES
//    ): ConfiguredFeature<*, *> {
//        return value.also {
//            BiomeModifications.addFeature(biomeSelector.environment, generationStep, key)
//        }
//    }
//
//    open fun registerOre(
//        location: ID,
//        value: ConfiguredFeature<*, *>,
//        biomeSelector: BiomeSelector = BiomeSelector.Overworld,
//        generationStep: GenerationStep.Feature = GenerationStep.Feature.UNDERGROUND_ORES
//    ): ConfiguredFeature<*, *> {
//        return registerOre(RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, location), value, biomeSelector, generationStep)
//    }
//
//    open fun registerOre(
//        path: String,
//        value: ConfiguredFeature<*, *>,
//        biomeSelector: BiomeSelector = BiomeSelector.Overworld,
//        generationStep: GenerationStep.Feature = GenerationStep.Feature.UNDERGROUND_ORES
//    ): ConfiguredFeature<*, *> {
//        return registerOre(Arcanology.identify(path), value, biomeSelector, generationStep)
//    }
//
//    /** Register a normal ore in an arbitrary dimension. */
//    open fun createOre(
//        /** The ID of the ore feature. */
//        ID: ID,
//        /** The ore to be generated. */
//        ore: OreBlock,
//        /** The amount of ore blocks in a ore blob. */
//        oreBlobSize: Int,
//        /** The number of ore blobs generated in a chunk. */
//        blobsPerChunk: Int,
//        /** The vertical range that this ore generates in. The first is minimum, second maximum. */
//        range: Pair<Int, Int>,
//        /** The biome selector used to determine where to generate the ore. */
//        biomeSelector: BiomeSelector,
//        /** The block that should be replaced by the ore. */
//        replaceable: Block = when (biomeSelector) {
//            BiomeSelector.Overworld -> Blocks.STONE
//            BiomeSelector.TheNether -> Blocks.NETHERRACK
//            BiomeSelector.TheEnd -> Blocks.END_STONE
//        }
//    ): ConfiguredFeature<OreFeatureConfig, OreFeature> {
//        return (Feature.ORE.configure(OreFeatureConfig(
//            BlockStateMatchRuleTest(replaceable.defaultState), ore.defaultState, oreBlobSize
//        )).range(range).repeat(blobsPerChunk).spreadHorizontally() as ConfiguredFeature<OreFeatureConfig, OreFeature>).also {
//            metadataRegistry[ID] = FeatureMetadata(biomeSelector, GenerationStep.Feature.UNDERGROUND_ORES)
//        }
//    }
//
//    /** Register a normal & deepslate overworld ore. */
//    open fun createNormalAndDeepslateOre(
//        /** The ID of the ore feature. */
//        ID: ID,
//        /** The non-deepslate ore. */
//        ore: OreBlock,
//        /** The deepslate ore variant. */
//        deepslateOre: OreBlock,
//        /** The amount of ore blocks in a ore blob. */
//        oreBlobSize: Int,
//        /** The number of ore blobs generated in a chunk. */
//        blobsPerChunk: Int,
//        /** The vertical range that this ore generates in. The first is minimum, second maximum. */
//        range: Pair<Int, Int>
//    ): ConfiguredFeature<OreFeatureConfig, OreFeature> {
//        return (Feature.ORE.configure(OreFeatureConfig(
//            listOf(
//                OreFeatureConfig.createTarget(OreFeatureConfig.Rules.STONE_ORE_REPLACEABLES, ore.defaultState),
//                OreFeatureConfig.createTarget(OreFeatureConfig.Rules.DEEPSLATE_ORE_REPLACEABLES, deepslateOre.defaultState)
//            ), oreBlobSize
//        )).range(range).repeat(blobsPerChunk).spreadHorizontally() as ConfiguredFeature<OreFeatureConfig, OreFeature>).also {
//            metadataRegistry[ID] = FeatureMetadata(BiomeSelector.Overworld, GenerationStep.Feature.UNDERGROUND_ORES)
//        }
//    }
//
//    /** Register an ore with a variable size. */
//    open fun createVariableOre(
//        /** The ID of the ore feature. */
//        ID: ID,
//        /** The ore to be generated. */
//        ore: OreBlock,
//        /** The vertical range that this ore generates in. The first is minimum, second maximum. */
//        range: Pair<Int, Int>,
//        /** The number of ore blobs generated in a chunk. */
//        blobsPerChunk: Int,
//        /** The biome selector used to determine where to generate the ore. */
//        biomeSelector: BiomeSelector,
//        /** The factory for the custom variable size. */
//        sizeFactory: (FeatureContext<CustomOreFeatureConfig>) -> Int = { fallbackSize },
//        /** The block that should be replaced by the ore. */
//        replaceable: Block = when(biomeSelector) {
//            BiomeSelector.Overworld -> Blocks.STONE
//            BiomeSelector.TheNether -> Blocks.NETHERRACK
//            BiomeSelector.TheEnd -> Blocks.END_STONE
//        },
//        /** The fallback size to be used, if either the sizeFactory is not overridden, or for some reason it cannot be used. */
//        fallbackSize: Int = 1
//    ): ConfiguredFeature<CustomOreFeatureConfig, CustomOreFeature> {
//        return (CustomOreFeature.instance.configure(CustomOreFeatureConfig(
//            BlockStateMatchRuleTest(replaceable.defaultState), ore.defaultState, fallbackSize, 1f, sizeFactory
//        )).range(range).repeat(blobsPerChunk).spreadHorizontally() as ConfiguredFeature<CustomOreFeatureConfig, CustomOreFeature>).also {
//            metadataRegistry[ID] = FeatureMetadata(biomeSelector, GenerationStep.Feature.UNDERGROUND_ORES)
//        }
//    }
//
//    companion object {
//        data class FeatureMetadata(val biomeSelector: BiomeSelector, val generationStep: GenerationStep.Feature)
//    }
}