@file:Suppress("UnstableApiUsage", "DEPRECATION")

package nichrosia.arcanology.type.world.data.storage.energy

import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant
import nichrosia.arcanology.type.world.data.energy.EnergyTier
import team.reborn.energy.api.EnergyStorage
import team.reborn.energy.api.base.SimpleEnergyStorage
import kotlin.math.min

/** A reimplementation of [SimpleEnergyStorage] using Kotlin properties to allow overriding them. */
open class ExtensibleEnergyStorage(open val energyCapacity: Long, open val maxInsert: Long, open val maxExtract: Long, initial: Long = 0L) : SnapshotParticipant<Long>(), EnergyStorage {
    open var energyAmount = initial

    init {
        StoragePreconditions.notNegative(energyCapacity)
        StoragePreconditions.notNegative(maxInsert)
        StoragePreconditions.notNegative(maxExtract)
        StoragePreconditions.notNegative(initial)
    }

    constructor(tier: EnergyTier, initial: Long = 0L) : this(tier.storage, tier.maxInputSpeed, tier.maxOutputSpeed, initial)

    override fun createSnapshot(): Long? {
        return energyAmount
    }

    override fun readSnapshot(snapshot: Long) {
        energyAmount = snapshot
    }

    override fun supportsInsertion(): Boolean {
        return maxInsert > 0
    }

    override fun insert(maxAmount: Long, transaction: TransactionContext): Long {
        StoragePreconditions.notNegative(maxAmount)

        val inserted = min(maxInsert, min(maxAmount, capacity - energyAmount))
        if (inserted > 0) {
            updateSnapshots(transaction)
            energyAmount += inserted
            return inserted
        }
        return 0
    }

    override fun supportsExtraction(): Boolean {
        return maxExtract > 0
    }

    override fun extract(maxAmount: Long, transaction: TransactionContext): Long {
        StoragePreconditions.notNegative(maxAmount)

        val extracted = min(maxExtract, min(maxAmount, energyAmount))
        if (extracted > 0) {
            updateSnapshots(transaction)
            energyAmount -= extracted
            return extracted
        }
        return 0
    }

    override fun getAmount(): Long {
        return energyAmount
    }

    override fun getCapacity(): Long {
        return energyCapacity
    }
}