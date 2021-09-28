package nichrosia.arcanology.data.ore

import net.minecraft.block.OreBlock
import net.minecraft.item.BlockItem
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.OreFeature
import net.minecraft.world.gen.feature.OreFeatureConfig

open class NormalAndDeepslateOre(block: OreBlock, item: BlockItem, val deepslateBlock: OreBlock, val deepslateItem: BlockItem, feature: ConfiguredFeature<OreFeatureConfig, OreFeature>) : Ore<OreFeatureConfig, OreFeature>(block, item, feature)