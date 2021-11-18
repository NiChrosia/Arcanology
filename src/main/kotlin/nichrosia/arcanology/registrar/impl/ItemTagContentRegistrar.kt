package nichrosia.arcanology.registrar.impl

import nichrosia.arcanology.Arcanology.arcanology
import nichrosia.arcanology.type.registar.tag.ItemTagRegistrar
import nichrosia.common.identity.ID
import nichrosia.common.record.registrar.Registrar

open class ItemTagContentRegistrar : ItemTagRegistrar.Basic() {
    val silicon by memberOf(ID("c", "silicon")) { nichrosia.arcanology.type.world.data.tag.ItemTag.Basic(it, Registrar.arcanology.item.silicon) }
}