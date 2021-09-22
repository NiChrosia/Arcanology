package nichrosia.arcanology.registry.lang.impl

import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.util.capitalize

open class BasicLanguageGenerator : LanguageGenerator {
    override fun generateLang(rawID: String): String {
        return rawID.split("_").joinToString(" ") { it.capitalize() }
    }
}