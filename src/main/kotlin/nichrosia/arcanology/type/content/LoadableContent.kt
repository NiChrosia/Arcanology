package nichrosia.arcanology.type.content

import net.minecraft.util.Identifier
import nichrosia.arcanology.util.capitalize
import nichrosia.arcanology.util.mapWords

/** A general wrapper for all content classes, with helper methods for [Identifier]s, automatically generating lang
 * files, and an abstract load method.
 * @author NiChrosia */
interface LoadableContent {
    fun load()

    fun generateLang(name: String): String {
        return name.replace("_", " ").mapWords { it.capitalize() }
    }
}