package arcanology.registrar.category

import arcanology.Arcanology
import arcanology.registrar.impl.ItemTagContentRegistrar
import arcanology.type.common.registar.tag.BlockTagRegistrar
import nichrosia.common.record.category.RegistrarCategory

@Suppress("unused")
open class TagCategory : RegistrarCategory(Arcanology.identify("tags")) {
    val blocks by registrarOf(BlockTagRegistrar::Basic)
    val items by registrarOf(::ItemTagContentRegistrar)
}