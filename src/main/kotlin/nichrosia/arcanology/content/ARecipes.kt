package nichrosia.arcanology.content

import nichrosia.arcanology.content.type.Content
import nichrosia.arcanology.recipe.RuneRecipe

object ARecipes : Content {
    override fun load() {
        // initialize singletons
        RuneRecipe.Companion.Type
        RuneRecipe.Companion.Serializer
    }
}