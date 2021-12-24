package arcanology.type.impl.world.block.entity.progressor

import arcanology.type.api.world.block.entity.progressor.Progressor
import net.minecraft.block.BlockState
import net.minecraft.inventory.Inventory
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

open class RecipeProgressor<I : Inventory, R : Recipe<I>, T : RecipeType<R>>(
    initial: Long,
    increment: () -> Long,
    max: Long,
    val inputSlots: Array<Int>,
    val outputSlot: Int,
    val entity: I,
    val type: T
) : Progressor(initial, increment, max) {
    override fun complete(world: World, pos: BlockPos, state: BlockState) {
        super.complete(world, pos, state)

        val recipe = world.recipeManager.getFirstMatch(type, entity, world)

        recipe.ifPresent(this::craft)
    }

    open fun craft(recipe: R) {
        val crafted = recipe.craft(entity)
        val output = entity.getStack(outputSlot)

        inputSlots.forEach {
            val inputStack = entity.getStack(it)

            if (!inputStack.isEmpty) {
                inputStack.decrement(1)
            }
        }

        if (output.isEmpty) {
            entity.setStack(outputSlot, crafted)
        } else {
            output.increment(1)
        }
    }
}