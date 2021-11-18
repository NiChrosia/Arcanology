package nichrosia.arcanology.registrar.impl

import net.minecraft.recipe.RecipeType
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.world.recipe.SmelterRecipe
import nichrosia.arcanology.type.registar.minecraft.VanillaRegistrar

open class RecipeTypeContentRegistrar : VanillaRegistrar.Basic<RecipeType<*>>(Registry.RECIPE_TYPE) {
    val smelter by memberOf(Arcanology.identify("smelting")) { SmelterRecipe.Type }
}