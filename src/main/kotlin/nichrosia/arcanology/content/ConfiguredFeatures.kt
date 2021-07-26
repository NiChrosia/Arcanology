@file:Suppress("deprecation")

package nichrosia.arcanology.content

import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
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
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.feature.util.FeatureContext
import nichrosia.arcanology.worldgen.CustomOreFeature
import nichrosia.arcanology.worldgen.CustomOreFeatureConfig
import kotlin.math.roundToInt
import nichrosia.arcanology.content.Blocks as ABlocks

open class ConfiguredFeatures : Loadable {
    override fun load() {
        velosiumOreEnd = registerOre(
            Identifier("arcanology", "velosium_ore_end"),
            Blocks.END_STONE,
            ABlocks.velosiumOre,
            2,
            { context ->
                clamp(
                    context.origin.getSquaredDistance(Vec3i.ZERO).roundToInt() / 2000,
                    2,
                    15
                )
            },
            12 to 65,
            3,
            true,
            BiomeSelector.TheEnd
        )

        aegiriteOreEnd = registerOre(
            Identifier("arcanology", "aegirite_ore_end"),
            Blocks.END_STONE,
            ABlocks.aegiriteOre,
            2,
            { context ->
                clamp(context.origin.getSquaredDistance(Vec3i.ZERO).roundToInt() / 4000, 2, 8)
            },
            34 to 56,
            4,
            true,
            BiomeSelector.TheEnd
        )
    }

    open fun registerOre(
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
        val key = RegistryKey.of(
            Registry.CONFIGURED_FEATURE_KEY,
            identifier
        )

        val oreFeature = Registry.register(
            BuiltinRegistries.CONFIGURED_FEATURE,
            key.value,
            CustomOreFeature.instance.configure(
                CustomOreFeatureConfig(
                    BlockStateMatchRuleTest(blockToReplace.defaultState),
                    oreBlock.defaultState,
                    size,
                    getSize
                )
            )
                .uniformRange(YOffset.aboveBottom(verticalRange.first), YOffset.fixed(verticalRange.second))
                .spreadHorizontally()
        )

        if (repeatRandomly) {
            oreFeature.repeatRandomly(repeat)
        } else {
            oreFeature.repeat(repeat)
        }

        BiomeModifications.addFeature(
            selector.environment,
            GenerationStep.Feature.UNDERGROUND_ORES,
            key
        )

        return oreFeature
    }

    companion object {
        lateinit var velosiumOreEnd: ConfiguredFeature<*, *>
        lateinit var aegiriteOreEnd: ConfiguredFeature<*, *>

        enum class BiomeSelector(val environment: (BiomeSelectionContext) -> Boolean) {
            TheEnd({ it.biome.category == Biome.Category.THEEND })
        }
    }
}