package nichrosia.arcanology.type.api.world.block.entity

import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeType
import net.minecraft.world.World
import nichrosia.arcanology.type.api.world.block.entity.inventory.SimpleInventory

interface EnergyRecipeBlockEntity<I, R : Recipe<I>, T : RecipeType<R>> : RecipeBlockEntity<I, R, T> where I : SimpleInventory, I : EnergyBlockEntity {
    override fun canProgress(inventory: I, world: World): Boolean {
        return super.canProgress(inventory, world) && inventory.energyStorage.amount >= inventory.tier.consumptionSpeed
    }
}