package nichrosia.arcanology.type.recipe

import com.google.gson.JsonObject
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.world.World
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.content.block.entity.RuneInfuserBlockEntity
import kotlin.reflect.KProperty0

@Suppress("LeakingThis")
open class RuneRecipe(
    open val result: ItemStack,
    open val lightItem: ItemStack?,
    open val voidItem: ItemStack?,
    open val fireItem: ItemStack?,
    open val waterItem: ItemStack?,
    open val earthItem: ItemStack?,
    open val airItem: ItemStack?
) : Recipe<RuneInfuserBlockEntity> {
    open val id: Int = run {
        types.add(this)

        types.indexOf(this)
    }

    open val inputItems: Array<KProperty0<ItemStack?>>
        get() = arrayOf(::lightItem, ::voidItem, ::fireItem, ::waterItem, ::earthItem, ::airItem)

    override fun getOutput(): ItemStack = result.copy()
    override fun getSerializer() = Serializer
    override fun getType() = Type
    override fun getId() = ID

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

        return result.copy()
    }

    override fun fits(width: Int, height: Int): Boolean {
        return true
    }

    companion object {
        val ID = Arcanology.idOf("rune_recipe")

        val types = mutableListOf<RuneRecipe>()

        fun of(id: Int): RuneRecipe {
            return types[id]
        }

        object Type : RecipeType<RuneRecipe> {
            init {
                Registry.register(Registry.RECIPE_TYPE, ID, this)
            }
        }

        @Suppress("MemberVisibilityCanBePrivate")
        object Serializer : RecipeSerializer<RuneRecipe> {
            init {
                Registry.register(Registry.RECIPE_SERIALIZER, ID, this)
            }

            override fun read(id: Identifier, buf: PacketByteBuf): RuneRecipe {
                return of(buf.readInt())
            }

            operator fun List<ItemStack>.component6() = this[5]
            operator fun List<ItemStack>.component7() = this[6]

            override fun read(id: Identifier, json: JsonObject): RuneRecipe {
                val (result, lightItem, voidItem, fireItem, waterItem, earthItem, airItem) = arrayOf(
                    "result",
                    "light",
                    "void",
                    "fire",
                    "water",
                    "earth",
                    "air"
                ).map {
                    val item = getItem(json, it + if (it == "result") "" else "_item")

                    if (item == null && it == "result") throw IllegalStateException("Result item cannot be null.")

                    ItemStack(item)
                }

                return RuneRecipe(result, lightItem, voidItem, fireItem, waterItem, earthItem, airItem)
            }

            override fun write(buf: PacketByteBuf, recipe: RuneRecipe) {
                buf.writeInt(recipe.id)
            }

            @Suppress("NestedLambdaShadowedImplicitParameter")
            fun getItem(json: JsonObject, name: String): Item? {
                return json.get(name)?.let {
                    if (it.isJsonObject) it.asJsonObject?.let {
                        it.get("item")?.let {
                            if (it.isJsonPrimitive) {
                                if (it.asJsonPrimitive.isString) {
                                    it.asJsonPrimitive.asString.let {
                                        val (namespace, path) = it.split(":")
                                        val itemID = Identifier(namespace, path)

                                        Registry.ITEM.get(itemID)
                                    }
                                } else null
                            } else null
                        }
                    } else null
                }
            }
        }

        val type: Type
            get() = Type

        val serializer: Serializer
            get() = Serializer
    }
}