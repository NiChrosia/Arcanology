package arcanology.type.api.common.world.block.entity

import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.block.entity.BlockEntity
import arcanology.type.common.world.data.storage.energy.BlockEntityEnergyStorage
import arcanology.type.common.world.data.energy.EnergyTier
import arcanology.type.common.world.data.nbt.NbtContainer
import team.reborn.energy.api.EnergyStorage

@Suppress("UnstableApiUsage")
interface EnergyBlockEntity {
    val tier: EnergyTier
    val energyStorage: EnergyStorage

    fun changeEnergyBy(amount: Long) {
        val transaction = Transaction.openOuter()

        when {
            amount > 0L -> energyStorage.insert(amount, transaction)
            amount < 0L -> energyStorage.extract(amount, transaction)
            else -> throw IllegalArgumentException("Cannot change energy by value of zero.")
        }

        transaction.commit()
    }

    fun <E> E.energyStorageOf(tier: EnergyTier = this.tier): BlockEntityEnergyStorage<E> where E : BlockEntity, E : NbtContainer, E : EnergyBlockEntity {
        return BlockEntityEnergyStorage(this, tier)
    }
}