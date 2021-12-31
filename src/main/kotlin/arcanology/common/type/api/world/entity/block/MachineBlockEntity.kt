package arcanology.common.type.api.world.entity.block

import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.inventory.SimpleInventory
import net.minecraft.util.math.BlockPos

abstract class MachineBlockEntity<E : MachineBlockEntity<E>>(
    type: BlockEntityType<E>,
    pos: BlockPos,
    state: BlockState
) : BlockEntity(type, pos, state), TickableBlockEntity<E> {
    open fun itemStorageOf(size: Int): CombinedStorage<ItemVariant, SingleSlotStorage<ItemVariant>> {
        val inventory = SimpleInventory(size)
        val storage = InventoryStorage.of(inventory, null)

        return CombinedStorage(storage.slots)
    }
}