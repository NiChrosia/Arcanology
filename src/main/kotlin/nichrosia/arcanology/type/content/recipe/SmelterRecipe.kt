package nichrosia.arcanology.type.content.recipe

import com.google.gson.JsonObject
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.world.World
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.content.block.entity.SmelterBlockEntity
import nichrosia.arcanology.util.capitalize
import nichrosia.common.identity.ID

open class SmelterRecipe(input: ItemStack, result: ItemStack) : MachineRecipe<SmelterBlockEntity, SmelterRecipe>(input, result, Companion::types) {
    override val ID: ID = Companion.ID
    override val recipeType: MachineRecipe.Type<SmelterBlockEntity, SmelterRecipe> = Type
    override val recipeSerializer: MachineRecipe.Serializer<SmelterBlockEntity, SmelterRecipe> = Serializer

    override fun matches(inventory: SmelterBlockEntity, world: World): Boolean {
        return inventory.items[inventory.inputSlots[0]].let { it.item == input.item && it.count >= input.count }
    }

    object Type : MachineRecipe.Type<SmelterBlockEntity, SmelterRecipe>(ID)

    object Serializer : MachineRecipe.Serializer<SmelterBlockEntity, SmelterRecipe>(ID, ::types) {
        override fun read(id: Identifier, json: JsonObject): SmelterRecipe {
            val (input, result) = arrayOf("input", "result").map {
                deserializeEitherItemStackJson(json, it) ?: throw IllegalStateException("${it.capitalize()} cannot be null.")
            }

            return SmelterRecipe(input, result)
        }
    }

    companion object {
        val ID = Arcanology.identify("separator")
        val types = mutableListOf<SmelterRecipe>()
    }
}