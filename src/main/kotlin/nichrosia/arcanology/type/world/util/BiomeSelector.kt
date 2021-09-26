@file:Suppress("deprecation")

package nichrosia.arcanology.type.world.util

import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.world.biome.Biome

@Suppress("unused")
enum class BiomeSelector(val environment: (BiomeSelectionContext) -> Boolean) {
    Overworld({ BiomeSelectors.foundInOverworld().test(it)  }),
    TheNether({ it.biome.category == Biome.Category.NETHER }),
    TheEnd({ it.biome.category == Biome.Category.THEEND })
}