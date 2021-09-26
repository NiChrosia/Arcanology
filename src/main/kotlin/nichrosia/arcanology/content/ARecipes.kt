package nichrosia.arcanology.content

import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import nichrosia.arcanology.type.content.LoadableContent
import nichrosia.arcanology.type.content.RegisterableContent
import nichrosia.arcanology.type.recipe.RuneRecipe

object ARecipes : LoadableContent, RegisterableContent<Unit, Pair<RecipeType<*>, RecipeSerializer<*>>> {
    override val registry: (Pair<RecipeType<*>, RecipeSerializer<*>>) -> Unit = {
        it.first
        it.second
    }

    override fun load() {
        register(RuneRecipe.type to RuneRecipe.serializer)
    }
}