package arcanology.common.type.impl.nbt.serializer

import arcanology.common.type.impl.world.storage.modular.ModularEnergyStorage
import arcanology.common.type.impl.world.storage.tier.EnergyTier
import dev.nathanpb.ktdatatag.serializer.DataSerializer
import net.minecraft.nbt.NbtCompound

open class ModularEnergyStorageSerializer(val tier: EnergyTier) : DataSerializer<ModularEnergyStorage> {
    override fun read(tag: NbtCompound, key: String): ModularEnergyStorage {
        val energy = tag.getCompound(key)
        val amount = energy.getLong("Amount")

        return ModularEnergyStorage(tier.capacity, tier.maxInsert, tier.maxExtract).also {
            it.amount = amount
        }
    }

    override fun write(tag: NbtCompound, key: String, data: ModularEnergyStorage) {
        val energy = NbtCompound()
        energy.putLong("Amount", data.amount)

        tag.put(key, energy)
    }
}