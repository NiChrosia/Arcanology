package nichrosia.arcanology.type.content.recipe

import com.google.gson.JsonObject
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.world.World
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.content.block.entity.SeparatorBlockEntity
import nichrosia.arcanology.type.recipe.MachineRecipe
import nichrosia.arcanology.util.capitalize

open class SeparatorRecipe(input: ItemStack, result: ItemStack) : MachineRecipe<SeparatorBlockEntity, SeparatorRecipe>(input, result, Companion::types) {
    override val ID: Identifier = Companion.ID
    override val recipeType: MachineRecipe.Type<SeparatorBlockEntity, SeparatorRecipe> = Type
    override val recipeSerializer: MachineRecipe.Serializer<SeparatorBlockEntity, SeparatorRecipe> = Serializer

    override fun matches(inventory: SeparatorBlockEntity, world: World): Boolean {
        return inventory.items[inventory.inputSlots[0]].let { it.item == input.item && it.count >= input.count }
    }

    object Type : MachineRecipe.Type<SeparatorBlockEntity, SeparatorRecipe>(ID)

    object Serializer : MachineRecipe.Serializer<SeparatorBlockEntity, SeparatorRecipe>(ID, ::types) {
        override fun read(id: Identifier, json: JsonObject): SeparatorRecipe {
            val (input, result) = arrayOf("input", "result").map {
                deserializeEitherItemStackJson(json, it) ?: throw IllegalStateException("${it.capitalize()} cannot be null.")
            }

            return SeparatorRecipe(input, result)
        }
    }

    companion object {
        val ID = Arcanology.idOf("separator")
        val types = mutableListOf<SeparatorRecipe>()
    }
}