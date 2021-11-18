package nichrosia.arcanology.type.world.recipe

import com.google.gson.JsonObject
import net.minecraft.item.ItemStack
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.type.api.world.block.entity.inventory.SimpleInventory
import nichrosia.arcanology.util.tryNumber
import nichrosia.arcanology.util.tryObject
import nichrosia.arcanology.util.tryPrimitive
import nichrosia.arcanology.util.tryString
import nichrosia.common.identity.ID

interface SimpleRecipe<I : SimpleInventory, T : SimpleRecipe<I, T>> : Recipe<I> {
    val type: Type<T>
    val serializer: Serializer<T>

    override fun getType(): RecipeType<*> {
        return type
    }

    override fun getSerializer(): RecipeSerializer<*> {
        return serializer
    }

    override fun getId(): Identifier {
        return type.ID
    }

    override fun fits(width: Int, height: Int): Boolean {
        return true
    }

    /** Crafts the recipe. This *will* perform side effects on the inventory. */
    override fun craft(inventory: I): ItemStack

    interface Type<T : SimpleRecipe<*, T>> : RecipeType<T> {
        val ID: ID

        open class Basic<T : SimpleRecipe<*, T>>(override val ID: ID) : Type<T>
    }

    interface Serializer<T : SimpleRecipe<*, T>> : RecipeSerializer<T>

    companion object {
        const val itemKey = "item"
        const val countKey = "count"

        fun JsonObject.deserializeItemStack(stackKey: String): ItemStack {
            val stackObject = this[stackKey].tryObject

            val item = stackObject?.get(itemKey)?.tryPrimitive?.tryString?.let {
                val ID = ID.deserialize(it)

                Registry.ITEM.get(ID)
            }

            val count = stackObject?.get(countKey)?.tryPrimitive?.tryNumber?.toInt()

            if (item == null || count == null) {
                throw IllegalStateException("Attempted to deserialize item stack with invalid data.")
            }

            return ItemStack(item, count)
        }
    }
}