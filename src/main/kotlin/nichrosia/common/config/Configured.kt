package nichrosia.common.config

/** A simple wrapper for configurable objects. */
@Suppress("UNCHECKED_CAST")
interface Configured<C : Config> {
    val config: C

    companion object {
        fun <C : Config, T : Configured<C>> T.configure(configurator: C.() -> Unit): T {
            return apply { config.apply(configurator) }
        }
    }
}