@file:Suppress("deprecation")

package nichrosia.arcanology.content

import com.google.common.collect.ImmutableList
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.block.Block
import net.minecraft.block.OreBlock
import net.minecraft.structure.rule.BlockStateMatchRuleTest
import net.minecraft.util.Identifier
import net.minecraft.util.math.Vec3i
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.biome.Biome
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.YOffset
import net.minecraft.world.gen.decorator.Decoratable
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.feature.util.FeatureContext
import nichrosia.arcanology.math.Math.clamp
import nichrosia.arcanology.type.world.feature.CustomOreFeature
import nichrosia.arcanology.type.world.feature.CustomOreFeatureConfig
import kotlin.math.roundToInt

@Suppress("MemberVisibilityCanBePrivate")
object AConfiguredFeatures : RegisterableContent<ConfiguredFeature<*, *>>(BuiltinRegistries.CONFIGURED_FEATURE) {
    @Suppress("unused")
    enum class BiomeSelector(val environment: (BiomeSelectionContext) -> Boolean) {
        Overworld({ BiomeSelectors.foundInOverworld().test(it)  }),
        TheNether({ it.biome.category == Biome.Category.NETHER }),
        TheEnd({ it.biome.category == Biome.Category.THEEND })
    }

    override fun load() {}

    fun <R> Decoratable<R>.uniformRange(min: Int, max: Int, generateToBottom: Boolean): R {
        return uniformRange(if (!generateToBottom) YOffset.aboveBottom(min) else YOffset.getBottom(), YOffset.fixed(max))
    }

    fun <R> Decoratable<R>.uniformRange(pair: Pair<Int, Int>, generateToBottom: Boolean): R {
        return uniformRange(pair.first, pair.second, generateToBottom)
    }

    fun <R> Decoratable<R>.repeat(amount: Int, randomly: Boolean): R {
        return if (randomly) repeatRandomly(amount) else repeat(amount)
    }

    fun distanceToOreSize(distancePerUnit: Int, min: Int, max: Int, useManhattan: Boolean = false): (FeatureContext<OreFeatureConfig>) -> Int {
        return { context ->
            (if (useManhattan) {
                context.origin.getManhattanDistance(Vec3i.ZERO)
            } else {
                context.origin.getSquaredDistance(Vec3i.ZERO).roundToInt()
            } / distancePerUnit).clamp(min, max)
        }
    }

    fun registerOre(
        identifier: Identifier,
        blockToReplace: Block,
        oreBlock: OreBlock,
        size: Int,
        getSize: (FeatureContext<OreFeatureConfig>) -> Int,
        verticalRange: Pair<Int, Int>,
        generateToBottom: Boolean = false,
        repeat: Int,
        repeatRandomly: Boolean = false,
        selector: BiomeSelector,
        deepslateVariant: Boolean = false,
        deepslateOre: OreBlock? = null
    ): ConfiguredFeature<*, *> {
        deepslateOre ?: if (deepslateVariant) {
            throw IllegalArgumentException("Deepslate ore cannot be null while deepslate variant is enabled.")
        }

        val key = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, identifier)

        val oreFeature = if (selector == BiomeSelector.Overworld && deepslateVariant) {
            register(key.value,
                Feature.ORE.configure(OreFeatureConfig(
                    ImmutableList.of(
                        OreFeatureConfig.createTarget(
                            OreFeatureConfig.Rules.STONE_ORE_REPLACEABLES,
                            oreBlock.defaultState
                        ),
                        OreFeatureConfig.createTarget(
                            OreFeatureConfig.Rules.DEEPSLATE_ORE_REPLACEABLES,
                            deepslateOre!!.defaultState,
                        )
                    ), size
                ))
                .uniformRange(verticalRange, generateToBottom)
                .spreadHorizontally()
                .repeat(repeat, repeatRandomly))
        } else {
            register(key.value,
                CustomOreFeature.instance.configure(
                    CustomOreFeatureConfig(BlockStateMatchRuleTest(blockToReplace.defaultState), oreBlock.defaultState, size, getSize)
                )
                .uniformRange(verticalRange, generateToBottom)
                .spreadHorizontally()
                .repeat(repeat, repeatRandomly))
        }

        BiomeModifications.addFeature(selector.environment, GenerationStep.Feature.UNDERGROUND_ORES, key)

        return oreFeature
    }
}