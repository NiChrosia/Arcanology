package arcanology.common.type.api.world.block

import arcanology.common.type.api.world.entity.block.MachineBlockEntity
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.world.World

abstract class MachineBlock<E : MachineBlockEntity<E>>(settings: Settings) : BlockWithEntity(settings), TickableBlock<MachineBlock<E>, E> {
    override fun <T : BlockEntity> getTicker(
        world: World,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return tickerOf(type)
    }

    override fun getRenderType(state: BlockState): BlockRenderType {
        return BlockRenderType.MODEL
    }
}