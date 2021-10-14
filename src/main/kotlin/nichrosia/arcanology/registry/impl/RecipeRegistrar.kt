package nichrosia.arcanology.registry.impl

import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import nichrosia.arcanology.registry.BasicRegistrar
import nichrosia.arcanology.registry.properties.RegistrarProperty
import nichrosia.arcanology.type.content.recipe.RuneRecipe
import nichrosia.arcanology.type.content.recipe.SeparatorRecipe

open class RecipeRegistrar : BasicRegistrar<Pair<RecipeType<*>, RecipeSerializer<*>>>() {
    val rune by RegistrarProperty("rune") { RuneRecipe.Companion.Type to RuneRecipe.Companion.Serializer }
    val separator by RegistrarProperty("separator") { SeparatorRecipe.Companion.Type to SeparatorRecipe.Companion.Serializer }
}