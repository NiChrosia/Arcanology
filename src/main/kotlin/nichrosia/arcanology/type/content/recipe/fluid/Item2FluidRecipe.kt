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
        val inputMatches = inputStackMatches(inventory.items[inventory.inputSlots[0]])
        val outputMatches = outputFluidMatches(inventory.fluidStorage.asStack)

        return inputMatches && outputMatches
    }

    override fun craft(inventory: I): ItemStack {
        inventory.removeStack(inventory.inputSlots[0], 1)

        inventory.fluidStorage.let {
            if (it.variant.fluid != output.fluid) it.variant = FluidVariant.of(output.fluid)

            it.fluidAmount += output.amount
        }

        return ItemStack.EMPTY.copy()
    }

    open fun inputStackMatches(stack: ItemStack): Boolean {
        val hasInput = stack.item == input.item
        val notEmpty = !stack.isEmpty

        return hasInput && notEmpty
    }

    open fun outputFluidMatches(stack: FluidStack): Boolean {
        val isEmpty = stack.fluid == Fluids.EMPTY
        val hasOutputFluid = stack.fluid == output.fluid
        val notFull = stack.amount <= stack.capacity - output.amount

        return (isEmpty || hasOutputFluid) && notFull
    }
}