package arcanology.common.type.impl.nbt.serializer

import arcanology.common.type.impl.world.storage.modular.ModularFluidStorage
import arcanology.common.type.impl.world.storage.tier.FluidTier
import dev.nathanpb.ktdatatag.serializer.DataSerializer
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.util.NbtType
import net.minecraft.nbt.NbtCompound
import net.minecraft.nbt.NbtList
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

open class ModularFluidStorageSerializer(val tier: FluidTier) : DataSerializer<ModularFluidStorage> {
    override fun read(tag: NbtCompound, key: String): ModularFluidStorage {
        val compound = tag.getCompound(key)

        val slots = compound.getList("Slots", NbtType.LIST).map {
            val slotCompound = it as NbtCompound

            val fluidId = slotCompound.getString("Fluid").let(::Identifier)
            val fluid = Registry.FLUID.get(fluidId)
            val variant = FluidVariant.of(fluid)

            val amount = slotCompound.getLong("Amount")

            ModularFluidStorage.FluidSlotStorage(variant, amount, tier.capacity)
        }.toMutableList()

        return ModularFluidStorage(slots)
    }

    override fun write(tag: NbtCompound, key: String, data: ModularFluidStorage) {
        val compound = NbtCompound()
        val list = NbtList()

        for (part in data.parts) {
            val slotCompound = NbtCompound()

            val fluid = part.resource.fluid
            val fluidId = Registry.FLUID.getId(fluid).toString()

            slotCompound.putString("Fluid", fluidId)
            slotCompound.putLong("Amount", part.amount)

            list.add(slotCompound)
        }

        compound.put("Slots", list)
        tag.put(key, compound)
    }
}