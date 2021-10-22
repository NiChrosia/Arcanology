@file:Suppress("DEPRECATION", "UnstableApiUsage")

package nichrosia.arcanology.type.storage.fluid

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant
import kotlin.math.min

open class SimpleFluidStorage(
    var fluid: FluidVariant? = null,
    val maxInsert: Long = 500L,
    val maxExtract: Long = 500L,
    val fluidCapacity: Long = FluidConstants.BUCKET,
    var fluidAmount: Long = 0L
) : SnapshotParticipant<Long>(), SingleSlotStorage<FluidVariant> {
    init {
        StoragePreconditions.notNegative(fluidCapacity)
        StoragePreconditions.notNegative(maxInsert)
        StoragePreconditions.notNegative(maxExtract)
    }

    override fun createSnapshot(): Long? {
        return fluidAmount
    }

    override fun readSnapshot(snapshot: Long) {
        fluidAmount = snapshot
    }

    override fun supportsInsertion(): Boolean {
        return maxInsert > 0L
    }

    override fun insert(resource: FluidVariant, maxAmount: Long, transaction: TransactionContext?): Long {
        StoragePreconditions.notNegative(maxAmount)

        val inserted = min(maxInsert, min(maxAmount, fluidCapacity - fluidAmount))

        return if (inserted > 0L) {
            updateSnapshots(transaction)
            fluidAmount += inserted
            inserted
        } else {
            0L
        }
    }

    override fun supportsExtraction(): Boolean {
        return maxExtract > 0L
    }

    override fun extract(resource: FluidVariant, maxAmount: Long, transaction: TransactionContext?): Long {
        StoragePreconditions.notNegative(maxAmount)

        val extracted = min(maxExtract, min(maxAmount, fluidAmount))

        return if (extracted > 0L) {
            updateSnapshots(transaction)
            fluidAmount -= extracted
            extracted
        } else {
            0L
        }
    }

    override fun getAmount(): Long {
        return fluidAmount
    }

    override fun getCapacity(): Long {
        return fluidCapacity
    }

    override fun getResource(): FluidVariant? {
        return fluid
    }

    override fun isResourceBlank(): Boolean {
        return fluid == null
    }
}