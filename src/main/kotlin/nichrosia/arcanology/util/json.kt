package nichrosia.arcanology.util

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive

///** Simple utility function to convert most primitive types to [JsonElement]s. */
//fun toSerializable(type: Any): JsonElement {
//    return when(type) {
//        is Number -> JsonPrimitive(type)
//        is Boolean -> JsonPrimitive(type)
//        is Char -> JsonPrimitive(type)
//        is JsonElement -> type
//        else -> JsonPrimitive(type.toString())
//    }
//}

///** Create a [JsonObject] from the given array of pairs. */
//fun jsonObject(vararg elements: Pair<String, Any>): JsonObject {
//    return JsonObject().apply { elements.forEach { add(it.first, toSerializable(it.second)) } }
//}
//
///** Create a [JsonArray] from the given array. */
//fun jsonArray(vararg elements: Any): JsonArray {
//    return JsonArray().apply { elements.forEach { add(toSerializable(it)) } }
//}

// Various utility values to make trying to access JSON types easier.

val JsonElement.tryObject: JsonObject?
    get() = if (isJsonObject) asJsonObject else null

val JsonElement.tryPrimitive: JsonPrimitive?
    get() = if (isJsonPrimitive) asJsonPrimitive else null

val JsonPrimitive.tryString: String?
    get() = if (isString) asString else null

val JsonPrimitive.tryNumber: Number?
    get() = if (isNumber) asNumber else null