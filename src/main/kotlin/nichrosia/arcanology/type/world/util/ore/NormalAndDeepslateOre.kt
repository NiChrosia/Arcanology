package nichrosia.arcanology.type.world.util.ore

import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.OreFeature
import net.minecraft.world.gen.feature.OreFeatureConfig
import nichrosia.arcanology.type.id.block.IdentifiedOreBlock
import nichrosia.arcanology.type.id.item.IdentifiedBlockItem

open class NormalAndDeepslateOre(
    block: IdentifiedOreBlock,
    item: IdentifiedBlockItem<IdentifiedOreBlock>,
    val deepslateBlock: IdentifiedOreBlock,
    val deepslateItem: IdentifiedBlockItem<IdentifiedOreBlock>,
    feature: ConfiguredFeature<OreFeatureConfig, OreFeature>
) : Ore<OreFeatureConfig, OreFeature>(block, item, feature)