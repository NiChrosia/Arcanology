package nichrosia.arcanology.registrar.impl

import net.minecraft.recipe.RecipeSerializer
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.world.recipe.SmelterRecipe
import nichrosia.arcanology.type.registar.minecraft.VanillaRegistrar

open class RecipeSerializerContentRegistrar : VanillaRegistrar.Basic<RecipeSerializer<*>>(Registry.RECIPE_SERIALIZER) {
    val smelter by memberOf(Arcanology.identify("smelting")) { SmelterRecipe.Serializer }
}