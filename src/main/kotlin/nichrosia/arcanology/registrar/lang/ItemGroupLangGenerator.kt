package nichrosia.arcanology.registrar.lang

import nichrosia.arcanology.util.capitalize
import nichrosia.common.identity.ID

open class ItemGroupLangGenerator : LangGenerator.Basic() {
    override fun generate(ID: ID): String {
        return ID.namespace.capitalize()
    }
}