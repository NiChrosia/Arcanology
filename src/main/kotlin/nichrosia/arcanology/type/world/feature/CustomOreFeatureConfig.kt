package nichrosia.arcanology.type.world.feature

import net.minecraft.block.BlockState
import net.minecraft.structure.rule.RuleTest
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.feature.util.FeatureContext

open class CustomOreFeatureConfig(
    targets: List<Target>,
    size: Int,
    discardOnAirChance: Float = 0.0f,
    val sizeFactory: (FeatureContext<CustomOreFeatureConfig>) -> Int = { size }
) : OreFeatureConfig(targets, size, discardOnAirChance) {
    constructor(test: RuleTest, state: BlockState, size: Int, discardOnAirChance: Float = 0.0f, getSize: (FeatureContext<CustomOreFeatureConfig>) -> Int = { size }) : this(listOf(createTarget(test, state)), size, discardOnAirChance, getSize)
}