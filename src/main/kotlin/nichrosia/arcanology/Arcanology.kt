package nichrosia.arcanology

import net.fabricmc.api.ModInitializer
import nichrosia.arcanology.content.*

@Suppress("unused")
open class Arcanology : ModInitializer {
    companion object {
        internal val content = arrayOf(
            Materials(),
            Blocks(),
            ConfiguredFeatures(),
            Items()
        )
    }

    override fun onInitialize() {
        content.forEach(Loadable::load)
    }
}