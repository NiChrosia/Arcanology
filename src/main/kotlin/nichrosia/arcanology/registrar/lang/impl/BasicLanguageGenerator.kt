package nichrosia.arcanology.registrar.lang.impl

import nichrosia.arcanology.registrar.lang.LanguageGenerator
import nichrosia.arcanology.util.capitalize

open class BasicLanguageGenerator : LanguageGenerator {
    override fun generateLang(rawID: String): String {
        return rawID.replace("_", " ").capitalize(true)
    }
}