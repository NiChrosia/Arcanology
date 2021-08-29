package nichrosia.arcanology.content

import net.minecraft.item.ItemStack
import nichrosia.arcanology.content.type.Content
import nichrosia.arcanology.recipe.RuneRecipe

object ARecipes : Content {
    override fun load() {
        // initialize singletons
        RuneRecipe.type
        RuneRecipe.serializer

        // fine, i'll instantiate it myself
        RuneRecipe(
            ItemStack(ARunes.manabound),
            ItemStack.EMPTY,
            ItemStack.EMPTY,
            ItemStack.EMPTY,
            ItemStack.EMPTY,
            ItemStack(AMaterials.terrene.magicCrystal),
            ItemStack.EMPTY
        )
    }
}