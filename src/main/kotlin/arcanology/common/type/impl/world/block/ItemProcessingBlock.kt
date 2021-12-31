package arcanology.common.type.impl.world.block

import arcanology.common.Arcanology
import arcanology.common.type.api.world.block.ScreenMachineBlock
import arcanology.common.type.impl.world.entity.block.ItemProcessingMachine
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos

open class ItemProcessingBlock(settings: Settings) : ScreenMachineBlock<ItemProcessingMachine>(settings) {
    override val type = Arcanology.content.blockEntity::itemProcessingMachine

    override fun createBlockEntity(pos: BlockPos, state: BlockState): ItemProcessingMachine {
        return ItemProcessingMachine(pos, state)
    }
}