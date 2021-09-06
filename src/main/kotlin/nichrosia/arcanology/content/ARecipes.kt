package nichrosia.arcanology.content

import nichrosia.arcanology.content.type.Content
import nichrosia.arcanology.type.recipe.RuneRecipe

object ARecipes : Content {
    override fun load() {
        // initialize singletons
        RuneRecipe.type
        RuneRecipe.serializer
    }
}