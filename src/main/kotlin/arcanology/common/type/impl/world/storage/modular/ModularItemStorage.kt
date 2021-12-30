package arcanology.common.type.impl.world.storage.modular

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack

open class ModularItemStorage(slots: MutableList<ItemSlotStorage>) : CombinedStorage<ItemVariant, ModularItemStorage.ItemSlotStorage>(slots), Toggleable, Inventory {
    override var enabled = false

    constructor(
        size: Int,
        default: ItemStack = ItemStack.EMPTY
    ) : this(Array(size) { default }.map(::ItemSlotStorage).toMutableList())

    override fun supportsInsertion(): Boolean {
        return super.supportsInsertion() && enabled
    }

    override fun supportsExtraction(): Boolean {
        return super.supportsExtraction() && enabled
    }

    override fun getStack(slot: Int): ItemStack {
        return parts[slot].stack
    }

    override fun setStack(slot: Int, stack: ItemStack) {
        parts[slot].stack = stack
    }

    override fun removeStack(slot: Int): ItemStack {
        val stack = parts[slot].stack.copy()

        return stack.also {
            setStack(slot, ItemStack.EMPTY)
        }
    }

    override fun removeStack(slot: Int, amount: Int): ItemStack {
        return if (getStack(slot).count > amount) {
            getStack(slot).decrement(amount)

            ItemStack(getStack(slot).item, amount)
        } else {
            removeStack(slot)
        }
    }

    override fun clear() {
        for (part in parts) {
            part.stack = ItemStack.EMPTY
        }
    }

    override fun markDirty() {

    }

    override fun canPlayerUse(player: PlayerEntity): Boolean {
        return true
    }

    override fun isEmpty(): Boolean {
        return parts.all { it.stack.isEmpty }
    }

    override fun size(): Int {
        return parts.size
    }

    open class ItemSlotStorage(var stack: ItemStack) : SingleSlotStorage<ItemVariant> {
        override fun getAmount(): Long {
            return stack.count.toLong()
        }

        override fun getCapacity(): Long {
            return stack.maxCount.toLong()
        }

        override fun getResource(): ItemVariant {
            return ItemVariant.of(stack)
        }

        override fun isResourceBlank(): Boolean {
            return stack.isEmpty
        }

        override fun insert(resource: ItemVariant, maxAmount: Long, transaction: TransactionContext): Long {
            return if (this.resource == resource || isResourceBlank) {
                if (amount + maxAmount <= capacity) {
                    maxAmount.also {
                        stack = ItemStack(resource.item, stack.count + it.toInt())
                    }
                } else {
                    val amount = capacity - amount

                    amount.also {
                        stack = ItemStack(resource.item, stack.count + it.toInt())
                    }
                }
            } else 0L
        }

        override fun extract(resource: ItemVariant, maxAmount: Long, transaction: TransactionContext): Long {
            return if (resource.matches(stack)) {
                if (amount - maxAmount >= 0) {
                    maxAmount.also {
                        stack.decrement(it.toInt())
                    }
                } else {
                    amount.also {
                        stack.decrement(it.toInt())
                    }
                }
            } else 0L
        }
    }
}