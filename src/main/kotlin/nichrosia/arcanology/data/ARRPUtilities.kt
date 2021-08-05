package nichrosia.arcanology.data

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import net.devtech.arrp.json.loot.JCondition
import net.devtech.arrp.json.loot.JLootTable.predicate as predicateFunction

object ARRPUtilities {
    @SafeVarargs
    /** @author Azagwen */
    private fun jsonObject(vararg elements: Pair<String, Any>): JsonObject {
        val jsonObject = JsonObject()

        for (element in elements) {
            when(element.second) {
                is Number -> jsonObject.addProperty(element.first, element.second as Number)
                is Boolean -> jsonObject.addProperty(element.first, element.second as Boolean)
                is Char -> jsonObject.addProperty(element.first, element.second as Char)
                is JsonElement -> jsonObject.add(element.first, element.second as JsonElement)
                else -> jsonObject.addProperty(element.first, element.second.toString())
            }
        }

        return jsonObject
    }

    /** @author Azagwen */
    private fun jsonArray(vararg elements: Any): JsonArray {
        val array = JsonArray()

        for (element in elements) {
            when(element) {
                is Number -> array.add(element)
                is Boolean -> array.add(element)
                is Char -> array.add(element)
                is JsonElement -> array.add(element)
                else -> array.add(element.toString())
            }
        }

        return array
    }
    
    /** @author Azagwen */
    fun silkTouchPredicate(): JCondition {
        val enchantment = JsonObject()
        enchantment.addProperty("enchantment", "minecraft:silk_touch")
        enchantment.add("levels", jsonObject("min" to 1))

        val predicate = JsonObject()
        predicate.add("enchantments", jsonArray(enchantment))

        return predicateFunction("minecraft:match_tool")
            .parameter("predicate", predicate)
    }
}