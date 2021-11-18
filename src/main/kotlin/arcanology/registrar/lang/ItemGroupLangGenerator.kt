package arcanology.registrar.lang

import arcanology.util.primitives.capitalize
import nichrosia.common.identity.ID

open class ItemGroupLangGenerator : LangGenerator.Basic() {
    override fun generate(ID: ID): String {
        return ID.namespace.capitalize()
    }
}