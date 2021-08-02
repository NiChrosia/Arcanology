package nichrosia.arcanology

import net.fabricmc.api.ModInitializer
import nichrosia.arcanology.content.*

@Suppress("unused")
object Arcanology : ModInitializer {
    internal val content = arrayOf(
        AMaterials,
        ABlocks,
        ABlockEntityTypes,
        AConfiguredFeatures,
        AItems,
        AStatusEffects
    )

    const val modID = "arcanology"

    override fun onInitialize() {
        content.forEach(Content::load)
    }
}