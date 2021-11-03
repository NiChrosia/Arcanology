package nichrosia.arcanology.util

// normal lists

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

// modifiable lists

fun <T, L : ModifiableList<T>> L.setAllTo(element: T) {
    indices.forEach { this[it] = element }
}

fun <T, C : Collection<T>> C.toModifiableList(): ModifiableList<T> {
    return ModifiableList(toMutableList())
}

fun <T> Array<T>.toModifiableList(): ModifiableList<T> {
    return ModifiableList(toMutableList())
}

inline fun <reified T> T.repeat(times: Int, noinline modifier: (T) -> T = { it }) = Array(times) { modifier(this) }.toModifiableList()

// component extensions

operator fun <T> List<T>.component6() = this[5]

// classes

/** A mutable list with a fixed size. */
open class ModifiableList<T>(protected val internal: MutableList<T> = mutableListOf()) : List<T> by internal {
    constructor(internal: ModifiableList<T>) : this(internal.internal)

    operator fun set(index: Int, value: T) {
        internal[index] = value
    }
}