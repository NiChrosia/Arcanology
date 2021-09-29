package nichrosia.arcanology.type.data.config.ore

import net.minecraft.block.Material
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.FeatureConfig
import nichrosia.arcanology.type.data.MaterialHelper
import nichrosia.arcanology.type.data.config.AbstractConfiguration
import nichrosia.arcanology.type.data.ore.Ore
import nichrosia.arcanology.type.world.util.BiomeSelector

abstract class OreConfiguration<C : FeatureConfig, F : Feature<C>>(
    name: String,
    val blockMaterial: Material = Material.STONE,
    val oreResistance: Float = 10f,
    val oreBlobSize: Int = 1,
    val blobsPerChunk: Int = 4,
    val range: Pair<Int, Int> = 0 to 50,
    val biomeSelector: BiomeSelector = BiomeSelector.Overworld,
    content: MaterialHelper.(OreConfiguration<C, F>) -> Ore<C, F>
) : AbstractConfiguration<Ore<C, F>, OreConfiguration<C, F>>(name, content)