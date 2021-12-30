package arcanology.common.type.impl.world.block

import arcanology.common.Arcanology
import arcanology.common.type.api.world.block.MachineBlock
import arcanology.common.type.impl.world.entity.block.ItemProcessingMachine

open class ItemProcessingBlock(settings: Settings) : MachineBlock<ItemProcessingMachine>(settings) {
    override val entityFactory = ::ItemProcessingMachine
    override val type = Arcanology.content.blockEntity::itemProcessingMachine
}