package arcanology.common.type.impl.world.storage.item

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack

open class StorageInventory(val backing: CombinedStorage<ItemVariant, ItemStackStorage>) : Inventory {
    override fun getStack(slot: Int): ItemStack {
        return backing.parts[slot].stack
    }

    override fun setStack(slot: Int, stack: ItemStack) {
        backing.parts[slot].stack = stack
    }

    override fun removeStack(slot: Int): ItemStack {
        val result = backing.parts[slot].stack.copy()

        return result.also {
            backing.parts[slot].stack = ItemStack.EMPTY.copy()
        }
    }

    override fun removeStack(slot: Int, amount: Int): ItemStack {
        return if (backing.parts[slot].stack.count > amount) {
            val result = ItemStack(backing.parts[slot].stack.item, amount)

            result.also {
                backing.parts[slot].stack.decrement(amount)
            }
        } else {
            removeStack(slot)
        }
    }

    override fun clear() {
        for (part in backing.parts) {
            part.stack = ItemStack.EMPTY.copy()
        }
    }

    override fun markDirty() {

    }

    override fun canPlayerUse(player: PlayerEntity): Boolean {
        return true
    }

    override fun isEmpty(): Boolean {
        return backing.parts.all { it.stack.isEmpty }
    }

    override fun size(): Int {
        return backing.parts.size
    }
}