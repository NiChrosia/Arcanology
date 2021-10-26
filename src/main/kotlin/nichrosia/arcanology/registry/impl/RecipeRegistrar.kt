package nichrosia.arcanology.registry.impl

import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.content.recipe.SeparatorRecipe
import nichrosia.registry.BasicRegistrar

open class RecipeRegistrar : BasicRegistrar<Pair<RecipeType<*>, RecipeSerializer<*>>>() {
    val separator by memberOf(Arcanology.identify("separator")) { SeparatorRecipe.Type to SeparatorRecipe.Serializer }
}