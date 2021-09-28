package nichrosia.arcanology.data.ore

import net.minecraft.block.OreBlock
import net.minecraft.item.BlockItem
import net.minecraft.world.gen.feature.*

open class Ore<C : FeatureConfig, F : Feature<C>>(val block: OreBlock, val item: BlockItem, val feature: ConfiguredFeature<C, F>)