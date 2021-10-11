package nichrosia.arcanology.registry.impl

import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import nichrosia.arcanology.registry.BasicRegistrar
import nichrosia.arcanology.registry.properties.RegistryProperty
import nichrosia.arcanology.type.content.recipe.RuneRecipe
import nichrosia.arcanology.type.content.recipe.SeparatorRecipe

open class RecipeRegistrar : BasicRegistrar<Pair<RecipeType<*>, RecipeSerializer<*>>>() {
    val rune by RegistryProperty("rune") { RuneRecipe.Companion.Type to RuneRecipe.Companion.Serializer }
    val separator by RegistryProperty("separator") { SeparatorRecipe.Companion.Type to SeparatorRecipe.Companion.Serializer }
}