@file:Suppress("DEPRECATION", "UnstableApiUsage")

package nichrosia.arcanology.type.world.data.storage.fluid

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.StoragePreconditions
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant
import net.minecraft.fluid.Fluids
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.type.world.data.nbt.NbtObject
import kotlin.math.min

open class SimpleFluidStorage(
    open var variant: FluidVariant = FluidVariant.blank(),
    open val maxInsert: Long = FluidConstants.BUCKET / 2,
    open val maxExtract: Long = FluidConstants.BUCKET / 2,
    open val fluidCapacity: Long = FluidConstants.BUCKET,
    initial: Long = 0L
) : SnapshotParticipant<Long>(), SingleSlotStorage<FluidVariant>, NbtObject {
    open var fluidAmount = initial

    open val asStack: FluidStack
        get() = FluidStack(variant.fluid, fluidAmount, fluidCapacity)

    init {
        StoragePreconditions.notNegative(fluidCapacity)
        StoragePreconditions.notNegative(maxInsert)
        StoragePreconditions.notNegative(maxExtract)
        StoragePreconditions.notNegative(initial)
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
            if (resource.fluid != variant.fluid) variant = FluidVariant.of(resource.fluid, resource.nbt)

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

        return if (extracted > 0L && fluidAmount - extracted > 0L) {
            updateSnapshots(transaction)

            fluidAmount -= extracted
            if (fluidAmount == 0L) variant = FluidVariant.blank()

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

    override fun getResource(): FluidVariant {
        return variant
    }

    override fun isResourceBlank(): Boolean {
        return variant.fluid == Fluids.EMPTY
    }

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        return nbt.apply {
            putString("fluid", Registry.FLUID.getId(variant.fluid).toString())
        }
    }

    override fun readNbt(nbt: NbtCompound) {
        nbt.apply {
            variant = FluidVariant.of(Registry.FLUID.get(Identifier(getString("fluid"))))
        }
    }
}