package nichrosia.arcanology.registry.impl

import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.content.recipe.RuneRecipe
import nichrosia.arcanology.type.content.recipe.SeparatorRecipe
import nichrosia.common.identity.ID
import nichrosia.registry.BasicRegistrar

open class RecipeRegistrar : BasicRegistrar<Pair<RecipeType<*>, RecipeSerializer<*>>>() {
    val rune by memberOf(ID(Arcanology.modID, "rune")) { RuneRecipe.Type to RuneRecipe.Serializer }
    val separator by memberOf(ID(Arcanology.modID, "separator")) { SeparatorRecipe.Type to SeparatorRecipe.Serializer }
}