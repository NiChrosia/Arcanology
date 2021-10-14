package nichrosia.arcanology.util

/** Get the next key after this one in the given [Map]. */
fun <V> Map<Long, V>.nextKey(key: Long): Long? {
    return (keys.indexOf(key) + 1).let { if (it == 0 || it >= keys.size) null else it }?.let { keys.toList()[it] }
}

/** Gets the closest value greater or equal to the value */
fun <V> Map<Long, V>.getClosestIncrement(value: Long): V {
    return this[keys.first {
        value >= it &&
        value < nextKey(it) ?: Long.MAX_VALUE
    }]!!
}