package nichrosia.arcanology.type.content.recipe

import com.google.gson.JsonObject
import net.minecraft.inventory.Inventory
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.world.World
import nichrosia.arcanology.util.tryNumber
import nichrosia.arcanology.util.tryObject
import nichrosia.arcanology.util.tryPrimitive
import nichrosia.arcanology.util.tryString
import nichrosia.common.identity.ID
import kotlin.reflect.KProperty0

@Suppress("UNCHECKED_CAST")
abstract class SimpleRecipe<I : Inventory, T : SimpleRecipe<I, T>>(val types: KProperty0<MutableList<T>>) : Recipe<I> {
    abstract val result: ItemStack
    abstract val ID: ID
    abstract val recipeType: Type<I, T>
    abstract val recipeSerializer: Serializer<I, T>

    open val packetID: Int = run {
        types().add(this as T)
        types().indexOf(this)
    }
    
    override fun matches(inventory: I, world: World): Boolean {
        return true
    }

    override fun craft(inventory: I): ItemStack {
        return result.copy()
    }

    override fun fits(width: Int, height: Int): Boolean {
        return true
    }

    override fun getOutput(): ItemStack {
        return result
    }

    override fun getId(): Identifier {
        return ID
    }

    override fun getSerializer(): RecipeSerializer<T> {
        return recipeSerializer
    }

    override fun getType(): RecipeType<T> {
        return recipeType
    }

    open class Type<I : Inventory, T : SimpleRecipe<I, T>>(ID: ID) : RecipeType<T> {
            init {
                Registry.register(Registry.RECIPE_TYPE, ID, this)
            }
        }

        abstract class Serializer<I : Inventory, T : SimpleRecipe<I, T>>(val ID: ID, val types: KProperty0<MutableList<T>>) : RecipeSerializer<T> {
            init {
                Registry.register(Registry.RECIPE_SERIALIZER, ID, this)
            }

            override fun read(id: Identifier, buf: PacketByteBuf): T {
                return types()[buf.readInt()]
            }

            override fun write(buf: PacketByteBuf, recipe: T) {
                buf.writeInt(recipe.packetID)
            }

            open fun deserializeItem(item: String): Item {
                val (namespace, path) = item.split(":")
                val ID = Identifier(namespace, path)

                return Registry.ITEM.get(ID)
            }

            open fun deserializeItemJson(json: JsonObject, name: String = "item"): Item? {
                return json[name].tryPrimitive?.tryString?.let { deserializeItem(it) }
            }

            open fun deserializeItemStackJson(json: JsonObject, name: String, itemName: String = "item", countName: String = "count"): ItemStack? {
                return json[name].tryObject?.run {
                    val item = this[itemName].tryPrimitive?.tryString
                    val count = this[countName].tryPrimitive?.tryNumber?.toInt()

                    item?.let { i ->
                        count?.let { c ->
                            ItemStack(deserializeItem(i), c)
                        }
                    }
                }
            }

            /** Attempt to deserialize a [JsonObject] into either an [ItemStack] of 1 count or x count from a JSON value. */
            open fun deserializeEitherItemStackJson(json: JsonObject, name: String, itemName: String = "item", countName: String = "count"): ItemStack? {
                return json[name].run {
                    when {
                        isJsonPrimitive && asJsonPrimitive.isString -> ItemStack(deserializeItem(asJsonPrimitive.asString), 1)
                        isJsonObject -> deserializeItemStackJson(json, name, itemName, countName)
                        else -> null
                    }
                }
            }
        }
}