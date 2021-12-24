package arcanology.type.impl.world.block

import arcanology.Arcanology
import arcanology.type.impl.world.block.entity.MachineBlockEntity
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

open class MachineBlock(settings: Settings) : BlockWithEntity(settings) {
    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return MachineBlockEntity(pos, state)
    }

    override fun <T : BlockEntity> getTicker(
        world: World,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return checkType(type, Arcanology.content.blockEntity.machine) { tickerWorld, tickerPos, tickerState, entity ->
            entity.tick(tickerWorld, tickerPos, tickerState)
        }
    }
}