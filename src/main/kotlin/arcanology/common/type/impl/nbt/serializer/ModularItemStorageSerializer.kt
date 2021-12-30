package arcanology.common.type.impl.nbt.serializer

import arcanology.common.type.impl.world.storage.modular.ModularItemStorage
import dev.nathanpb.ktdatatag.serializer.DataSerializer
import net.fabricmc.fabric.api.util.NbtType
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

open class ModularItemStorageSerializer : DataSerializer<ModularItemStorage> {
    override fun read(tag: NbtCompound, key: String): ModularItemStorage {
        val compound = tag.getCompound(key)

        val slots = compound.getList("Slots", NbtType.LIST).map {
            val slotCompound = it as NbtCompound

            val itemId = slotCompound.getString("Item").let(::Identifier)
            val item = Registry.ITEM.get(itemId)

            val count = slotCompound.getInt("Count")

            val stack = ItemStack(item, count)

            ModularItemStorage.ItemSlotStorage(stack)
        }.toMutableList()

        return ModularItemStorage(slots)
    }

    override fun write(tag: NbtCompound, key: String, data: ModularItemStorage) {
        val compound = NbtCompound()
        val list = NbtList()

        for (slot in data.parts) {
            val slotCompound = NbtCompound()

            val item = slot.stack.item
            val itemId = Registry.ITEM.getId(item).toString()

            slotCompound.putString("Item", itemId)
            slotCompound.putInt("Count", slot.stack.count)

            list.add(slotCompound)
        }

        compound.put("Slots", list)
        tag.put(key, compound)
    }
}