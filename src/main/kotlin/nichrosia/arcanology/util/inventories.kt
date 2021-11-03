package nichrosia.arcanology.util

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList

fun NbtCompound.putInventory(inventory: ModifiableList<ItemStack>, putEmptyList: Boolean = true): NbtCompound {
    return apply {
        val list = NbtList()

        inventory.forEachIndexed { index, stack ->
            if (!stack.isEmpty) {
                val compound = NbtCompound().let {
                    putByte("Slot", index.toByte())
                    stack.writeNbt(it)
                }

                list.add(compound)
            }
        }

        if (!list.isEmpty() || putEmptyList) {
            put("Items", list)
        }
    }
}

fun NbtCompound.readToInventory(inventory: ModifiableList<ItemStack>): NbtCompound {
    return apply {
        val list = getList("Items", 10)
        list.nbtType

        for (index in list.indices) {
            val compound = list.getCompound(index)
            val slot = compound.getByte("Slot").toInt() and 255

            if (slot >= 0 && slot < inventory.size) {
                inventory[slot] = ItemStack.fromNbt(compound)
            }
        }
    }
}