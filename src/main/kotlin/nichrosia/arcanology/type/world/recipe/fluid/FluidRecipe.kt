@file:Suppress("DEPRECATION", "UnstableApiUsage")

package nichrosia.arcanology.type.world.recipe.fluid

import com.google.gson.JsonObject
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.type.world.block.entity.FluidMachineBlockEntity
import nichrosia.arcanology.type.world.recipe.SimpleRecipe
import nichrosia.arcanology.type.world.data.storage.fluid.FluidStack
import nichrosia.arcanology.type.world.data.storage.fluid.SimpleFluidStorage
import nichrosia.arcanology.util.tryNumber
import nichrosia.arcanology.util.tryObject
import nichrosia.arcanology.util.tryPrimitive
import nichrosia.arcanology.util.tryString
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