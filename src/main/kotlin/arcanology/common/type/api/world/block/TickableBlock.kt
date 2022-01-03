package arcanology.common.type.api.world.block

import arcanology.common.type.api.world.entity.block.TickableBlockEntity
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.BlockWithEntity.checkType
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType

interface TickableBlock<B, E> where B : BlockWithEntity, E : BlockEntity, E : TickableBlockEntity<E> {
    val type: () -> BlockEntityType<E>

    fun <T : BlockEntity> B.tickerOf(given: BlockEntityType<T>): BlockEntityTicker<T>? { // return type is cursed, but it's Mojang's fault
        return checkType(given, type()) { world, pos, state, entity ->
            entity?.apply {
                tick(world, pos, state)
            }
        }
    }
}