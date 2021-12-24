package arcanology.content

import arcanology.Arcanology
import nucleus.common.builtin.division.content.ItemGroupRegistrar

open class AItemGroupRegistrar(root: Arcanology) : ItemGroupRegistrar<Arcanology>(root) {
    val main by memberOf(root.identify("main")) { groupOf(it) }.apply {
        lang(::readableEnglish)
    }
}