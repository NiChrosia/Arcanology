package arcanology.content

import arcanology.Arcanology
import net.minecraft.item.BlockItem
import nucleus.common.builtin.division.content.GroupedItemRegistrar

open class AItemRegistrar(root: Arcanology) : GroupedItemRegistrar<Arcanology>(root, { root.content.itemGroup.main }) {
    val machine by memberOf(root.identify("test")) { BlockItem(root.content.block.machine, groupedSettings) }.apply {
        lang(::readableEnglish)
        model { blockModel(root.content.block.machine) }
        loot { blockLoot(root.content.block.machine) }
    }
}