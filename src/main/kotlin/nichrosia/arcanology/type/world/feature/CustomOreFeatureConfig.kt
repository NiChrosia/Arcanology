package nichrosia.arcanology.type.world.feature

import net.minecraft.block.BlockState
import net.minecraft.structure.rule.RuleTest
import net.minecraft.world.gen.feature.OreFeatureConfig
import net.minecraft.world.gen.feature.util.FeatureContext

open class CustomOreFeatureConfig(
    test: RuleTest,
    state: BlockState,
    size: Int,
    val getSize: (FeatureContext<OreFeatureConfig>) -> Int = { size }
) : OreFeatureConfig(test, state, size)