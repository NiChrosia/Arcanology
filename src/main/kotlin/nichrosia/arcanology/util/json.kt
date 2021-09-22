package nichrosia.arcanology.util

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive

fun toSerializable(type: Any): JsonElement {
    return when(type) {
        is Number -> JsonPrimitive(type)
        is Boolean -> JsonPrimitive(type)
        is Char -> JsonPrimitive(type)
        is JsonElement -> type
        else -> JsonPrimitive(type.toString())
    }
}

fun jsonObject(vararg elements: Pair<String, Any>): JsonObject {
    return JsonObject().apply { elements.forEach { add(it.first, toSerializable(it.second)) } }
}

fun jsonArray(vararg elements: Any): JsonArray {
    return JsonArray().apply { elements.forEach { add(toSerializable(it)) } }
}