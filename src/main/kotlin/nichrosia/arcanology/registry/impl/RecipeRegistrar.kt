package nichrosia.arcanology.registry.impl

import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.content.recipe.SmelterRecipe
import nichrosia.common.identity.ID
import nichrosia.common.registry.type.basic.BasicContentRegistrar

open class RecipeRegistrar : BasicContentRegistrar<Pair<RecipeType<*>, RecipeSerializer<*>>>() {
    val smelter by memberOf(Arcanology.identify("smelting")) { SmelterRecipe.Type to SmelterRecipe.Serializer }

    override fun <E : Pair<RecipeType<*>, RecipeSerializer<*>>> register(input: ID, output: E): E {
        return super.register(input, output).also {
            val (type, serializer) = it

            Registry.register(Registry.RECIPE_TYPE, input, type)
            Registry.register(Registry.RECIPE_SERIALIZER, input, serializer)
        }
    }
}