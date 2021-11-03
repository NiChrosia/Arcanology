package nichrosia.arcanology.type.content.block.entity.inventory

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.util.math.Direction
import nichrosia.arcanology.util.decrement
import nichrosia.arcanology.util.increment
import nichrosia.arcanology.util.setAllTo

interface BasicInventory : SidedInventory {
    val items: MutableList<ItemStack>
    val inputSlots: Array<Int>

    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction) = true
    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?) = inputSlots.contains(slot)
    override fun getAvailableSlots(side: Direction) = inputSlots.toIntArray()
    override fun size() = items.size
    override fun getStack(slot: Int) = items[slot]
    override fun clear() = items.setAllTo(ItemStack.EMPTY)
    override fun canPlayerUse(player: PlayerEntity) = true
    override fun isEmpty() = items.all { it.isEmpty }


    /** Removes items from an inventory slot.
     * @param slot  The slot to remove from.
     * @param count How many items to remove. If there are less items in the slot than what are requested,
     * takes all items in that slot. */
    override fun removeStack(slot: Int, count: Int): ItemStack {
        val result = Inventories.splitStack(items.toList(), slot, count)

        if (!result.isEmpty) {
            markDirty()
        }

        return result
    }

    override fun removeStack(slot: Int): ItemStack {
        return Inventories.removeStack(items.toList(), slot)
    }

    /** Replaces the current stack in an inventory slot with the provided stack.
     * @param slot  The inventory slot of which to replace the itemstack.
     * @param stack The replacing itemstack. If the stack is too big for
     * this inventory ([Inventory.getMaxCountPerStack]),
     * it gets resized to this inventory's maximum amount. */
    override fun setStack(slot: Int, stack: ItemStack) {
        items[slot] = stack.copy()

        if (items[slot].count > maxCountPerStack) {
            items[slot].count = maxCountPerStack
        }
    }

    fun incrementStack(slot: Int, defaultStack: ItemStack): ItemStack {
        if (items[slot].isEmpty) {
            setStack(slot, defaultStack)
        } else {
            setStack(slot, items[slot].copy().increment())
        }

        return items[slot]
    }

    fun decrementStack(slot: Int, zeroStack: ItemStack = ItemStack.EMPTY): ItemStack {
        val stack = items[slot]

        return when {
            stack.isEmpty -> zeroStack.copy()
            stack.count > 1 -> {
                setStack(slot, stack.decrement(zeroStack.copy()))
                items[slot]
            }
            else -> {
                setStack(slot, zeroStack.copy())
                items[slot]
            }
        }
    }
}