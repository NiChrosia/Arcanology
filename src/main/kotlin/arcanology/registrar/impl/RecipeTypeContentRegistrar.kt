package arcanology.registrar.impl

import net.minecraft.recipe.RecipeType
import net.minecraft.util.registry.Registry
import arcanology.Arcanology
import arcanology.type.common.world.recipe.SmelterRecipe
import arcanology.type.common.registar.minecraft.VanillaRegistrar

open class RecipeTypeContentRegistrar : VanillaRegistrar.Basic<RecipeType<*>>(Registry.RECIPE_TYPE) {
    val smelter by memberOf(Arcanology.identify("smelting")) { SmelterRecipe.Type }
}