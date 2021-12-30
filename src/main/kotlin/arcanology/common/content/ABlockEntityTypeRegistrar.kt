package arcanology.common.content

import arcanology.common.Arcanology
import arcanology.common.type.impl.world.entity.block.ItemProcessingMachine
import nucleus.common.builtin.division.content.BlockEntityTypeRegistrar

open class ABlockEntityTypeRegistrar(root: Arcanology) : BlockEntityTypeRegistrar<Arcanology>(root) {
    val itemProcessingMachine by memberOf(root.identify("item_processing_machine")) { typeOf(::ItemProcessingMachine, root.content.block.itemProcessingMachine) }
}