package arcanology.type.api.world.block.entity.inventory

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack

interface ItemInventory : Inventory {
    val items: Array<ItemStack>

    override fun getStack(slot: Int): ItemStack {
        return items[slot]
    }

    override fun removeStack(slot: Int): ItemStack {
        return ItemStack.EMPTY.copy().also {
            items[slot] = it
        }
    }

    override fun removeStack(slot: Int, amount: Int): ItemStack {
        val stack = items[slot]

        if (amount >= stack.count) {
            val split = stack.copy()

            items[slot] = ItemStack.EMPTY.copy()

            return split
        }

        stack.decrement(amount)

        return ItemStack(stack.item, amount)
    }

    override fun setStack(slot: Int, stack: ItemStack) {
        items[slot] = stack
    }

    override fun clear() {
        items.indices.forEach {
            items[it] = ItemStack.EMPTY
        }
    }

    override fun isEmpty(): Boolean {
        return items.all(ItemStack::isEmpty)
    }

    override fun size(): Int {
        return items.size
    }

    override fun canPlayerUse(player: PlayerEntity): Boolean {
        return true
    }

    fun emptyInventory(size: Int = 0): Array<ItemStack> {
        return Array(size) { ItemStack.EMPTY }
    }
}