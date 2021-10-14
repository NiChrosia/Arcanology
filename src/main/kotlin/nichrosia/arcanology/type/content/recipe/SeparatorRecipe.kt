package nichrosia.arcanology.type.content.recipe

import com.google.gson.JsonObject
import net.minecraft.item.ItemStack
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.util.Identifier
import net.minecraft.world.World
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.content.block.entity.SeparatorBlockEntity
import nichrosia.arcanology.type.recipe.MachineRecipe
import nichrosia.arcanology.type.recipe.SimpleRecipe
import nichrosia.arcanology.util.capitalize

open class SeparatorRecipe(input: ItemStack, result: ItemStack) : MachineRecipe<SeparatorBlockEntity, SeparatorRecipe>(input, result, Companion::types) {
    override val ID: Identifier = Companion.ID
    override val recipeType: RecipeType<SeparatorRecipe> = Type
    override val recipeSerializer: RecipeSerializer<SeparatorRecipe> = Serializer

    override fun matches(inventory: SeparatorBlockEntity, world: World): Boolean {
        return inventory.items[inventory.inputSlots[0]].let { it.item == input.item && it.count >= input.count }
    }

    override fun craft(inventory: SeparatorBlockEntity): ItemStack {
        return super.craft(inventory)
    }

    companion object {
        val ID = Arcanology.idOf("separator")
        val types = mutableListOf<SeparatorRecipe>()

        object Type : SimpleRecipe.Companion.Type<SeparatorBlockEntity, SeparatorRecipe>(ID)

        object Serializer : MachineRecipe.Companion.Serializer<SeparatorBlockEntity, SeparatorRecipe>(ID, ::types) {
            override fun read(id: Identifier, json: JsonObject): SeparatorRecipe {
                val (input, result) = arrayOf("input", "result").map {
                    deserializeEitherItemStackJson(json, it) ?: throw IllegalStateException("${it.capitalize()} cannot be null.")
                }

                return SeparatorRecipe(input, result)
            }
        }
    }
}