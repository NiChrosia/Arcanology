package nichrosia.arcanology.content

import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.minecraft.block.Blocks
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
import nichrosia.arcanology.math.intprovider.CustomIntProvider
import nichrosia.arcanology.worldgen.CustomOreFeature
import nichrosia.arcanology.worldgen.CustomOreFeatureConfig
import kotlin.math.roundToInt
import nichrosia.arcanology.content.Blocks as ABlocks

open class ConfiguredFeatures : Loadable {
    override fun load() {
        val velosiumOreEndRegistryKey = RegistryKey.of(
            Registry.CONFIGURED_FEATURE_KEY,
            Identifier("arcanology", "velosium_ore_end")
        )

        velosiumOreEnd = Registry.register(
            BuiltinRegistries.CONFIGURED_FEATURE,
            velosiumOreEndRegistryKey.value,
            CustomOreFeature.instance.configure(
                CustomOreFeatureConfig(
                    BlockStateMatchRuleTest(Blocks.END_STONE.defaultState),
                    ABlocks.velosiumOre.defaultState,
                    2
                ) { context ->
                    clamp(
                        context.origin.getSquaredDistance(Vec3i.ZERO).roundToInt(),
                        2,
                        15
                    )
                }
            )
            .uniformRange(YOffset.aboveBottom(12), YOffset.fixed(65))
            .spreadHorizontally()
            .repeatRandomly(3)
        )

        BiomeModifications.addFeature(
            { context ->
                context.biome.category == Biome.Category.THEEND
            },
            GenerationStep.Feature.UNDERGROUND_ORES,
            velosiumOreEndRegistryKey
        )
    }

    companion object {
        lateinit var velosiumOreEnd: ConfiguredFeature<*, *>
    }
}