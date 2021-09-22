package nichrosia.arcanology.registry.lang.impl

import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.util.capitalize

/** A simple language generator generate language in the following format: "Arcanology: Group" */
open class ItemGroupLanguageGenerator : LanguageGenerator {
    override fun generateLang(rawID: String): String {
        return "${Arcanology.modID.capitalize()}: ${rawID.split("_").joinToString(" ") { it.capitalize() }}"
    }
}