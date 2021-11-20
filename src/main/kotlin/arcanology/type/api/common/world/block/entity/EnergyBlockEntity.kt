package arcanology.type.api.common.world.block.entity

import arcanology.type.common.world.block.machine.Machine
import arcanology.type.common.world.data.energy.EnergyTier
import arcanology.type.common.world.data.nbt.NbtContainer
import arcanology.type.common.world.data.storage.energy.BlockEntityEnergyStorage
import arcanology.type.graphics.ui.gui.property.KPropertyDelegate
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.block.entity.BlockEntity

@Suppress("UnstableApiUsage")
interface EnergyBlockEntity {
    val energyStorage: BlockEntityEnergyStorage<*>

    fun changeEnergyBy(amount: Long) {
        val transaction = Transaction.openOuter()

        when {
            amount > 0L -> energyStorage.insert(amount, transaction)
            amount < 0L -> energyStorage.extract(amount, transaction)
            else -> throw IllegalArgumentException("Cannot change energy by value of zero.")
        }

        transaction.commit()
    }

    fun <E> E.energyStorageOf(tier: EnergyTier): BlockEntityEnergyStorage<E> where E : BlockEntity, E : NbtContainer, E : EnergyBlockEntity {
        return BlockEntityEnergyStorage(this, tier)
    }

    fun <E> E.energyPropertyDelegateOf(tier: EnergyTier): KPropertyDelegate where E : BlockEntity, E : EnergyBlockEntity, E : Machine {
        return KPropertyDelegate(recipeProgressor::progress, recipeProgressor::maxProgress, energyStorage::energyAmount, energyStorage::energyCapacity)
    }
}