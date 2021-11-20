package arcanology.util.collections.iterables

import arcanology.util.collections.ModifiableList

fun <T, L : List<T>> L.next(element: T, loop: Boolean = true): T {
    val index = indexOf(element)
    var next = index + 1

    if (next == size) {
        if (loop) {
            next = 0
        } else {
            throw IllegalArgumentException("Attempted to get the element after the last with looping disabled.")
        }
    }

    return this[next]
}

fun <T, L : List<T>> L.previous(element: T, loop: Boolean = true): T {
    val index = indexOf(element)
    var previous = index - 1

    if (previous <= -1) {
        if (loop) {
            previous = lastIndex
        } else {
            throw IllegalArgumentException("Attempted to get the element before the previous with looping disabled.")
        }
    }

    return this[previous]
}

fun <L : List<Long>> L.productBy(base: Long = 1L): Long {
    var original = base

    forEach { original *= it }

    return original
}

fun <E, L : MutableList<E>> L.search(value: E, create: Boolean = true): E? {
    val present = contains(value)

    if (!present && create) add(value)

    return if (!present && !create) null else value
}

fun <T, L : ModifiableList<T>> L.setAllTo(element: T) {
    indices.forEach { this[it] = element }
}

fun <T, C : Collection<T>> C.toModifiableList(): ModifiableList<T> {
    return ModifiableList(toMutableList())
}

fun <T> Array<T>.toModifiableList(): ModifiableList<T> {
    return ModifiableList(toMutableList())
}