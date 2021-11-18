@file:Suppress("DEPRECATION", "UnstableApiUsage")

package arcanology.type.common.world.recipe.fluid

import com.google.gson.JsonObject
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.util.registry.Registry
import arcanology.type.common.world.block.entity.FluidMachineBlockEntity
import arcanology.type.common.world.recipe.SimpleRecipe
import arcanology.type.common.world.data.storage.fluid.FluidStack
import arcanology.type.common.world.data.storage.fluid.SimpleFluidStorage
import arcanology.util.data.tryNumber
import arcanology.util.data.tryObject
import arcanology.util.data.tryPrimitive
import arcanology.util.data.tryString
import nichrosia.common.identity.ID

interface FluidRecipe<I : FluidMachineBlockEntity<*, *, *, I>, T : FluidRecipe<I, T>> : SimpleRecipe<I, T> {
    companion object {
        const val fluidKey = "fluid"
        const val amountKey = "amount"

        fun JsonObject.deserializeFluid(stackKey: String): FluidStack {
            val stackObject = this[stackKey].tryObject

            val fluid = stackObject?.get(fluidKey)?.tryPrimitive?.tryString?.let {
                val ID = ID.deserialize(it)

                Registry.FLUID.get(ID)
            }

            val amount = stackObject?.get(amountKey)?.tryPrimitive?.tryNumber?.toLong()

            if (fluid == null || amount == null) {
                throw IllegalStateException("Attempted to deserialize fluid with invalid data.")
            }

            return FluidStack(fluid, amount)
        }

        fun JsonObject.deserializeFluidTo(stackKey: String, storage: SimpleFluidStorage) {
            val (fluid, amount) = deserializeFluid(stackKey)

            storage.variant = FluidVariant.of(fluid)
            storage.fluidAmount = amount
        }
    }
}