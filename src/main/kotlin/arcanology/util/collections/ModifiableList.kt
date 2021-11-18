package arcanology.util.collections

/** A mutable list with a fixed size. */
open class ModifiableList<T>(protected val internal: MutableList<T> = mutableListOf()) : List<T> by internal {
    operator fun set(index: Int, value: T) {
        internal[index] = value
    }
}