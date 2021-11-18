package nichrosia.arcanology.registrar.lang

import nichrosia.arcanology.util.capitalize
import nichrosia.common.identity.ID

interface LangGenerator {
    fun generate(rawID: String): String
    fun generate(ID: ID): String = generate(ID.path)

    open class Basic : LangGenerator {
        override fun generate(rawID: String): String {
            return rawID.replace("_", " ").capitalize(true)
        }
    }
}