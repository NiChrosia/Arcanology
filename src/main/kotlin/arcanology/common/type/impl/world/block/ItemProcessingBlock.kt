package arcanology.common.type.impl.world.block

import arcanology.common.Arcanology
import arcanology.common.type.api.world.block.ScreenMachineBlock
import arcanology.common.type.impl.world.entity.block.ItemProcessingMachine

open class ItemProcessingBlock(settings: Settings) : ScreenMachineBlock<ItemProcessingMachine>(settings) {
    override val entityFactory = ::ItemProcessingMachine
    override val type = Arcanology.content.blockEntity::itemProcessingMachine
}