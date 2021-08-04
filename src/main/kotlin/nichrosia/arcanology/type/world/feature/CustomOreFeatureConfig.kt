package nichrosia.arcanology.type.world.feature

import net.minecraft.block.BlockState
import net.minecraft.structure.rule.RuleTest
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.feature.util.FeatureContext

open class CustomOreFeatureConfig(
    targets: List<Target>,
    size: Int,
    val getSize: (FeatureContext<OreFeatureConfig>) -> Int = { size }
) : OreFeatureConfig(targets, size, 0.0f) {
    constructor(test: RuleTest, state: BlockState, size: Int, getSize: (FeatureContext<OreFeatureConfig>) -> Int = { size }) : this(listOf(createTarget(test, state)), size, getSize)
}