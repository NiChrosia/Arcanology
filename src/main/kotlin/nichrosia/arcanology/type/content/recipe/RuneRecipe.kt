package nichrosia.arcanology.type.content.recipe

import com.google.gson.JsonObject
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.world.World
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.content.block.entity.RuneInfuserBlockEntity
import nichrosia.arcanology.util.component6
import kotlin.reflect.KProperty0

@Suppress("LeakingThis")
open class RuneRecipe(
    override val result: ItemStack,
    open val lightItem: ItemStack?,
    open val voidItem: ItemStack?,
    open val fireItem: ItemStack?,
    open val waterItem: ItemStack?,
    open val earthItem: ItemStack?,
    open val airItem: ItemStack?
) : SimpleRecipe<RuneInfuserBlockEntity, RuneRecipe>(Companion::types) {
    override val ID: Identifier = Companion.ID
    override val recipeType: SimpleRecipe.Type<RuneInfuserBlockEntity, RuneRecipe> = Type
    override val recipeSerializer: SimpleRecipe.Serializer<RuneInfuserBlockEntity, RuneRecipe> = Serializer

    open val inputItems: Array<KProperty0<ItemStack?>>
        get() = arrayOf(::lightItem, ::voidItem, ::fireItem, ::waterItem, ::earthItem, ::airItem)

    override fun matches(inventory: RuneInfuserBlockEntity, world: World): Boolean {
        return (lightItem?.item == inventory.lightSlot.item || lightItem?.isEmpty == true) &&
                (voidItem?.item == inventory.voidSlot.item || voidItem?.isEmpty == true) &&
                (fireItem?.item == inventory.fireSlot.item || fireItem?.isEmpty == true) &&
                (waterItem?.item == inventory.waterSlot.item || waterItem?.isEmpty == true) &&
                (earthItem?.item == inventory.earthSlot.item || earthItem?.isEmpty == true) &&
                (airItem?.item == inventory.airSlot.item || airItem?.isEmpty == true)
    }

    override fun craft(inventory: RuneInfuserBlockEntity): ItemStack {
        inventory.consumeRecipeIngredients(this)

        return super.craft(inventory)
    }

    object Type : SimpleRecipe.Type<RuneInfuserBlockEntity, RuneRecipe>(ID)

    object Serializer : SimpleRecipe.Serializer<RuneInfuserBlockEntity, RuneRecipe>(ID, Companion::types) {
        override fun read(id: Identifier, json: JsonObject): RuneRecipe {
            val (lightItem, voidItem, fireItem, waterItem, earthItem, airItem) = arrayOf(
                "light",
                "void",
                "fire",
                "water",
                "earth",
                "air"
            ).map { deserializeEitherItemStackJson(json, "${it}_item") }

            val result = deserializeEitherItemStackJson(json, "result") ?: throw IllegalStateException("Result cannot be null.")

            return RuneRecipe(result, lightItem, voidItem, fireItem, waterItem, earthItem, airItem)
        }
    }

    companion object {
        val ID = Arcanology.idOf("rune")
        val types = mutableListOf<RuneRecipe>()
    }
}