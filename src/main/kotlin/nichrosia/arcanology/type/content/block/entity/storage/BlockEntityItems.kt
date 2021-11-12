package nichrosia.arcanology.type.content.block.entity.storage

import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import nichrosia.arcanology.type.nbt.NbtContainer
import nichrosia.arcanology.type.nbt.NbtObject
import nichrosia.arcanology.util.ModifiableList
import nichrosia.arcanology.util.putInventory
import nichrosia.arcanology.util.readToInventory

open class BlockEntityItems(container: NbtContainer, items: MutableList<ItemStack> = mutableListOf()) : ModifiableList<ItemStack>(items), NbtObject {
    init {
        container.nbtObjects.add(this)
    }

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        return nbt.putInventory(this)
    }

    override fun readNbt(nbt: NbtCompound) {
        nbt.readToInventory(this)
    }
}