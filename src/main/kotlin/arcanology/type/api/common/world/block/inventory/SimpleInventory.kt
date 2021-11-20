package arcanology.type.api.common.world.block.inventory

import arcanology.type.common.world.data.nbt.NbtObject
import arcanology.util.collections.iterables.repeat
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.util.math.Direction

interface SimpleInventory : SidedInventory {
    val items: Array<ItemStack>

    val inputSides: Array<Direction>
    val outputSides: Array<Direction>

    val inputSlots: Array<Int>
    val outputSlots: Array<Int>

    val slotSize: Int
        get() = inputSlots.size + outputSlots.size

    val inventoryNbtObject: NbtObject
        get() = object : NbtObject {
            override fun writeNbt(nbt: NbtCompound): NbtCompound {
                return nbt.apply {
                    val list = NbtList()

                    items.forEachIndexed { slot, stack ->
                        val slotCompound = NbtCompound()

                        slotCompound.putInt("Slot", slot)
                        slotCompound.put("Stack", stack.writeNbt(NbtCompound()))

                        list.add(slotCompound)
                    }

                    put("Items", list)
                }
            }

            override fun readNbt(nbt: NbtCompound) {
                nbt.apply {
                    val itemsNbt = get("Items") ?: throw IllegalArgumentException("Provided NbtCompound does not contain items.")

                    if (itemsNbt is NbtList) {
                        itemsNbt.forEach { compound ->
                            if (compound is NbtCompound) {
                                val slot = compound.getInt("Slot")
                                val stack = ItemStack.fromNbt(compound.getCompound("Stack"))

                                items[slot] = stack
                            }
                        }
                    }
                }
            }
        }

    // from SidedInventory

    override fun getStack(slot: Int): ItemStack {
        return items[slot]
    }

    override fun setStack(slot: Int, stack: ItemStack) {
        items[slot] = stack
    }

    override fun removeStack(slot: Int, amount: Int): ItemStack {
        val stack = items[slot]

        return if (stack.count > amount) {
            stack.apply {
                count -= amount
            }
        } else {
            removeStack(slot)
        }
    }

    override fun removeStack(slot: Int): ItemStack {
        return ItemStack.EMPTY.copy().apply {
            items[slot] = this
        }
    }

    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?): Boolean {
        return dir?.let(inputSides::contains) ?: false && inputSlots.contains(slot)
    }

    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction): Boolean {
        return outputSides.contains(dir) && outputSlots.contains(slot)
    }

    override fun getAvailableSlots(side: Direction): IntArray {
        val slots = inputSlots.filter { canInsert(it, ItemStack.EMPTY, side) }

        return slots.toIntArray()
    }

    override fun canPlayerUse(player: PlayerEntity): Boolean {
        return true
    }

    override fun isEmpty(): Boolean {
        return items.isEmpty()
    }

    override fun size(): Int {
        return items.size
    }

    override fun clear() {
        items.indices.forEach {
            items[it] = ItemStack.EMPTY
        }
    }

    fun <E> E.emptyInventoryOf(size: Int): Array<ItemStack> where E : BlockEntity,  E : SimpleInventory {
        return ItemStack.EMPTY.repeat(size, ItemStack::copy)
    }
}