package nichrosia.arcanology.registry.impl

import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.content.recipe.SmelterRecipe
import nichrosia.common.registry.type.basic.BasicContentRegistrar

open class RecipeRegistrar : BasicContentRegistrar<Pair<RecipeType<*>, RecipeSerializer<*>>>() {
    val smelter by memberOf(Arcanology.identify("smelter")) { SmelterRecipe.Type to SmelterRecipe.Serializer }
}