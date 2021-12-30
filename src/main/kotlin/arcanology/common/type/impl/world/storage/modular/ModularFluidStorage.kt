package arcanology.common.type.impl.world.storage.modular

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext

open class ModularFluidStorage(slots: MutableList<out SingleSlotStorage<FluidVariant>>) : CombinedStorage<FluidVariant, SingleSlotStorage<FluidVariant>>(slots), Toggleable {
    override var enabled = false

    constructor(size: Int, capacity: Long, variant: FluidVariant = FluidVariant.blank(), amount: Long = 0L) : this(Array(size) { FluidSlotStorage(variant, amount, capacity) }.toMutableList())

    override fun supportsInsertion(): Boolean {
        return super.supportsInsertion() && enabled
    }

    override fun supportsExtraction(): Boolean {
        return super.supportsExtraction() && enabled
    }

    open class FluidSlotStorage(var variant: FluidVariant, @JvmField var amount: Long, @JvmField val capacity: Long) : SingleSlotStorage<FluidVariant> {
        override fun getResource(): FluidVariant {
            return variant
        }

        override fun getAmount(): Long {
            return amount
        }

        override fun getCapacity(): Long {
            return capacity
        }

        override fun isResourceBlank(): Boolean {
            return variant.isBlank || amount <= 0L
        }

        override fun insert(resource: FluidVariant, maxAmount: Long, transaction: TransactionContext): Long {
            return if (amount + maxAmount <= capacity) {
                maxAmount.also {
                    amount += it
                }
            } else {
                val inserted = capacity - amount

                inserted.also {
                    amount += it
                }
            }
        }

        override fun extract(resource: FluidVariant, maxAmount: Long, transaction: TransactionContext): Long {
            return if (amount - maxAmount >= 0) {
                maxAmount.also {
                    amount -= it
                }
            } else {
                amount.also {
                    amount = 0
                }
            }
        }
    }
}