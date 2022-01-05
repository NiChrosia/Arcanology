package arcanology.common.type.api.world.entity.block

import arcanology.common.type.impl.world.storage.item.ItemStackStorage
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage
import net.fabricmc.fabric.api.util.NbtType
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.network.Packet
import net.minecraft.network.listener.ClientPlayPacketListener
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket
import net.minecraft.util.math.BlockPos

abstract class MachineBlockEntity<E : MachineBlockEntity<E>>(
    type: BlockEntityType<E>,
    pos: BlockPos,
    state: BlockState
) : BlockEntity(type, pos, state), TickableBlockEntity<E> {
    override fun toUpdatePacket(): Packet<ClientPlayPacketListener> {
        return BlockEntityUpdateS2CPacket.create(this) {
            NbtCompound().also(this::writeNbt)
        }
    }

    open fun itemStorageOf(size: Int): CombinedStorage<ItemVariant, ItemStackStorage> {
        val slots = mutableListOf<ItemStackStorage>()

        for (x in 1..size) {
            val stack = ItemStack.EMPTY.copy()
            val storage = ItemStackStorage(stack)

            slots.add(storage)
        }

        return CombinedStorage(slots)
    }

    open fun NbtCompound.putItemStorage(key: String, storage: CombinedStorage<ItemVariant, ItemStackStorage>) {
        val items = NbtList()

        for (part in storage.parts) {
            val compound = NbtCompound()

            part.stack.writeNbt(compound)

            items.add(compound)
        }

        put(key, items)
    }

    open fun NbtCompound.getItemStorage(key: String, storage: CombinedStorage<ItemVariant, ItemStackStorage>): CombinedStorage<ItemVariant, ItemStackStorage> {
        val list = getList(key, NbtType.LIST)

        for (i in 0 until list.size) {
            val compound = list.getCompound(i)

            val stack = ItemStack.fromNbt(compound)

            storage.parts[i].stack = stack
        }

        return storage
    }
}