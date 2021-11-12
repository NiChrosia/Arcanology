package nichrosia.arcanology.type.content.api.block.entity

import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import team.reborn.energy.api.EnergyStorage

@Suppress("UnstableApiUsage")
interface EnergyBlockEntity {
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
}