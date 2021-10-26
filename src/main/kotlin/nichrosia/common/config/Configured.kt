package nichrosia.common.config

/** A simple wrapper for configurable objects. */
@Suppress("UNCHECKED_CAST")
interface Configured<C : Config, out T : Configured<C, T>> {
    val config: C
}