package arcanology.common.content

import arcanology.common.Arcanology
import nucleus.common.builtin.division.content.ItemGroupRegistrar

class AItemGroupRegistrar(root: Arcanology) : ItemGroupRegistrar<Arcanology>(root) {
    val main by memberOf(root.identify("main")) { groupOf(it) }.apply {
        lang(::readableEnglish)
    }
}