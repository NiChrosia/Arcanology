package arcanology.registrar.impl

import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.registry.Registry
import arcanology.Arcanology
import arcanology.type.common.world.recipe.SmelterRecipe
import arcanology.type.common.registar.minecraft.VanillaRegistrar

open class RecipeSerializerContentRegistrar : VanillaRegistrar.Basic<RecipeSerializer<*>>(Registry.RECIPE_SERIALIZER) {
    val smelter by memberOf(Arcanology.identify("smelting")) { SmelterRecipe.Serializer }
}