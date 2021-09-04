package nichrosia.arcanology.content

import net.minecraft.item.ItemStack
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.content.type.Content
import nichrosia.arcanology.recipe.RuneRecipe

object ARecipes : Content {
    override fun load() {
        // initialize singletons
        RuneRecipe.type
        RuneRecipe.serializer
    }
}