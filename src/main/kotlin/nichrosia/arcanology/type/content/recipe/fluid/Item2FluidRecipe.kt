@file:Suppress("DEPRECATION", "UnstableApiUsage")

package nichrosia.arcanology.type.content.recipe.fluid

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import nichrosia.arcanology.type.content.block.entity.FluidMachineBlockEntity
import nichrosia.arcanology.type.stack.FluidStack

abstract class Item2FluidRecipe<I : FluidMachineBlockEntity<*, *, *, I>, T : Item2FluidRecipe<I, T>>(open val input: ItemStack, open val output: FluidStack) : FluidRecipe<I, T> {
    override fun getOutput(): ItemStack {
        return ItemStack.EMPTY
    }

    override fun matches(inventory: I, world: World): Boolean {
        return inventory.items[inventory.inputSlots[0]].let {
            val hasInput = it.item == input.item
            val notEmpty = !it.isEmpty

            hasInput && notEmpty
        } && inventory.fluidStorage.let {
            val isEmpty = it.variant.fluid == Fluids.EMPTY
            val hasOutputFluid = it.variant.fluid == output.fluid
            val notFull = it.fluidAmount <= it.fluidCapacity - output.amount

            (isEmpty || hasOutputFluid) && notFull
        }
    }

    override fun craft(inventory: I): ItemStack {
        inventory.decrementStack(inventory.inputSlots[0])

        inventory.fluidStorage.let {
            if (it.variant.fluid != output.fluid) it.variant = FluidVariant.of(output.fluid)

            it.fluidAmount += output.amount
        }

        return ItemStack.EMPTY.copy()
    }
}