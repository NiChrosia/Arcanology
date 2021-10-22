package nichrosia.arcanology.type.world.util.ore

import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.FeatureConfig
import nichrosia.arcanology.type.id.block.IdentifiedOreBlock
import nichrosia.arcanology.type.id.item.IdentifiedBlockItem

open class Ore<C : FeatureConfig, F : Feature<C>>(val block: IdentifiedOreBlock, val item: IdentifiedBlockItem<IdentifiedOreBlock>, val feature: ConfiguredFeature<C, F>)