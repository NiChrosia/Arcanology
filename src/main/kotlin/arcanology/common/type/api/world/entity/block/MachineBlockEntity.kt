package arcanology.common.type.api.world.entity.block

import arcanology.common.type.impl.world.storage.item.ItemStackStorage
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos

abstract class MachineBlockEntity<E : MachineBlockEntity<E>>(
    type: BlockEntityType<E>,
    pos: BlockPos,
    state: BlockState
) : BlockEntity(type, pos, state), TickableBlockEntity<E> {
    open fun itemStorageOf(size: Int): CombinedStorage<ItemVariant, ItemStackStorage> {
        val slots = mutableListOf<ItemStackStorage>()

        for (x in 1..size) {
            val stack = ItemStack.EMPTY.copy()
            val storage = ItemStackStorage(stack)

            slots.add(storage)
        }

        return CombinedStorage(slots)
    }


}