package arcanology.common.content

import arcanology.common.Arcanology
import nucleus.common.builtin.division.content.GroupedItemRegistrar

open class AItemRegistrar(root: Arcanology) : GroupedItemRegistrar<Arcanology>(root, { root.content.itemGroup.main }) {
//    val machine by memberOf(root.identify("machine")) { BlockItem(root.content.block.machine, groupedSettings) }.apply {
//        lang(::readableEnglish)
//        model { blockModel(root.content.block.machine) }
//        loot { blockLoot(root.content.block.machine) }
//    }
}