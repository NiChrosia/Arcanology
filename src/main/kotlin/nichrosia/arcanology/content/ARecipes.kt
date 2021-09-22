package nichrosia.arcanology.content

import nichrosia.arcanology.type.content.LoadableContent
import nichrosia.arcanology.type.recipe.RuneRecipe

object ARecipes : LoadableContent {
    override fun load() {
        RuneRecipe.type; RuneRecipe.serializer
    }
}