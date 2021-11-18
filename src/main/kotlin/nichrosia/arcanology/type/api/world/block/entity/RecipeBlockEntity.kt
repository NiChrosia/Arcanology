package nichrosia.arcanology.type.api.world.block.entity

import net.minecraft.block.BlockState
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import nichrosia.arcanology.type.api.world.block.entity.inventory.SimpleInventory
import nichrosia.arcanology.type.world.block.MachineBlock
import nichrosia.arcanology.util.setState

interface RecipeBlockEntity<I : SimpleInventory, R : Recipe<I>, T : RecipeType<R>> {
    val recipeType: T
    var progress: Long

    fun canProgress(inventory: I, world: World): Boolean {
        return inventory.inputSlots.any { !inventory.items[it].isEmpty } && world.recipeManager.getFirstMatch(recipeType, inventory, world).isPresent
    }

    fun onRecipeCompletion(inventory: I, world: World, pos: BlockPos, state: BlockState) {
        progress = 0L

        world.recipeManager.getFirstMatch(recipeType, inventory, world).ifPresent { it.craft(inventory) }
        if (inventory.items[inventory.inputSlots[0]].isEmpty) setState(world, pos, state, MachineBlock.active, false)

        inventory.markDirty()
    }
}