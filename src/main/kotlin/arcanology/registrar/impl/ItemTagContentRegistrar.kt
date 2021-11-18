package arcanology.registrar.impl

import arcanology.Arcanology.arcanology
import arcanology.type.common.registar.tag.ItemTagRegistrar
import nichrosia.common.identity.ID
import nichrosia.common.record.registrar.Registrar

open class ItemTagContentRegistrar : ItemTagRegistrar.Basic() {
    val silicon by memberOf(ID("c", "silicon")) { arcanology.type.common.world.data.tag.ItemTag.Basic(it, Registrar.arcanology.item.silicon) }
}