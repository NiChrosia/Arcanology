package arcanology.common.content

import arcanology.common.Arcanology
import net.minecraft.item.BlockItem
import nucleus.common.builtin.division.content.GroupedItemRegistrar

class AItemRegistrar(root: Arcanology) : GroupedItemRegistrar<Arcanology>(root, { root.content.itemGroup.main }) {
    val machine by memberOf(root.identify("item_processing_machine")) { BlockItem(root.content.block.itemProcessingMachine, groupedSettings) }.apply {
        lang(::readableEnglish)
        model { blockModel(root.content.block.itemProcessingMachine) }
        loot { blockLoot(root.content.block.itemProcessingMachine) }
    }
}