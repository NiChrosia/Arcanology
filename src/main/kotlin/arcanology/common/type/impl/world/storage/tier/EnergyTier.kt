package arcanology.common.type.impl.world.storage.tier

import team.reborn.energy.api.base.SimpleEnergyStorage

data class EnergyTier(val capacity: Long, val maxInsertion: Long = Long.MAX_VALUE, val maxExtraction: Long = Long.MAX_VALUE) {
    fun storageOf(amount: Long = 0L): SimpleEnergyStorage {
        val storage = SimpleEnergyStorage(capacity, maxInsertion, maxExtraction)
        storage.amount = amount

        return storage
    }

    fun fullStorageOf(): SimpleEnergyStorage {
        return storageOf(capacity)
    }
}