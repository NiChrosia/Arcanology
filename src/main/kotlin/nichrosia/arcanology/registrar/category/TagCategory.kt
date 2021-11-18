package nichrosia.arcanology.registrar.category

import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registrar.impl.ItemTagContentRegistrar
import nichrosia.arcanology.type.registar.tag.BlockTagRegistrar
import nichrosia.common.record.category.RegistrarCategory

@Suppress("unused")
open class TagCategory : RegistrarCategory(Arcanology.identify("tags")) {
    val blocks by registrarOf(BlockTagRegistrar::Basic)
    val items by registrarOf(::ItemTagContentRegistrar)
}