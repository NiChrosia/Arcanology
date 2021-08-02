@file:Suppress("deprecation")

package nichrosia.arcanology.content

import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.OreBlock
import net.minecraft.client.util.MonitorTracker.clamp
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
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.feature.util.FeatureContext
import nichrosia.arcanology.type.world.feature.CustomOreFeature
import nichrosia.arcanology.type.world.feature.CustomOreFeatureConfig
import kotlin.math.roundToInt
import nichrosia.arcanology.content.ABlocks as ABlocks

@Suppress("MemberVisibilityCanBePrivate")
object AConfiguredFeatures : RegisterableContent<ConfiguredFeature<*, *>>(BuiltinRegistries.CONFIGURED_FEATURE) {
    lateinit var velosiumOreEnd: ConfiguredFeature<*, *>
    lateinit var aegiriteOreEnd: ConfiguredFeature<*, *>
    lateinit var xenothiteOreEnd: ConfiguredFeature<*, *>

    @Suppress("unused")
    enum class BiomeSelector(val environment: (BiomeSelectionContext) -> Boolean) {
        Overworld({ it.biome.category == Biome.Category.NONE }),
        TheNether({ it.biome.category == Biome.Category.NETHER }),
        TheEnd({ it.biome.category == Biome.Category.THEEND })
    }

    override fun load() {
        velosiumOreEnd = registerOre(
            identify("velosium_ore_end"),
            Blocks.END_STONE,
            ABlocks.velosiumOre,
            2,
            distanceToOreSize(2000, 2, 15),
            12 to 65,
            3,
            true,
            BiomeSelector.TheEnd
        )

        aegiriteOreEnd = registerOre(
            identify("aegirite_ore_end"),
            Blocks.END_STONE,
            ABlocks.aegiriteOre,
            2,
            distanceToOreSize(4000, 2, 8),
            34 to 56,
            4,
            true,
            BiomeSelector.TheEnd
        )

        xenothiteOreEnd = registerOre(
            identify("xenothite_ore_end"),
            Blocks.END_STONE,
            ABlocks.xenothiteOre,
            2,
            distanceToOreSize(10000, 1, 3),
            12 to 56,
            2,
            false,
            BiomeSelector.TheEnd
        )
    }

    fun <R> Decoratable<R>.uniformRange(min: Int, max: Int): R {
        return uniformRange(YOffset.aboveBottom(min), YOffset.fixed(max))
    }

    fun <R> Decoratable<R>.uniformRange(pair: Pair<Int, Int>): R {
        return uniformRange(pair.first, pair.second)
    }

    fun <R> Decoratable<R>.repeat(amount: Int, randomly: Boolean): R {
        return if (randomly) repeatRandomly(amount) else repeat(amount)
    }

    fun distanceToOreSize(distancePerUnit: Int, min: Int, max: Int, useManhattan: Boolean = false): (FeatureContext<OreFeatureConfig>) -> Int {
        return { context ->
            clamp(
                if (useManhattan) {
                    context.origin.getManhattanDistance(Vec3i.ZERO)
                } else {
                    context.origin.getSquaredDistance(Vec3i.ZERO).roundToInt()
                } / distancePerUnit,
                min,
                max
            )
        }
    }

    fun registerOre(
        identifier: Identifier,
        blockToReplace: Block,
        oreBlock: OreBlock,
        size: Int,
        getSize: (FeatureContext<OreFeatureConfig>) -> Int,
        verticalRange: Pair<Int, Int>,
        repeat: Int,
        repeatRandomly: Boolean = false,
        selector: BiomeSelector
    ): ConfiguredFeature<*, *> {
        val key = RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, identifier)

        val oreFeature = register(
            key.value,
            CustomOreFeature.instance.configure(
                CustomOreFeatureConfig(
                    BlockStateMatchRuleTest(blockToReplace.defaultState), oreBlock.defaultState,
                    size, getSize
                )
            )
                .uniformRange(verticalRange)
                .spreadHorizontally()
                .repeat(repeat, repeatRandomly)
        )

        BiomeModifications.addFeature(selector.environment, GenerationStep.Feature.UNDERGROUND_ORES, key)

        return oreFeature
    }

}