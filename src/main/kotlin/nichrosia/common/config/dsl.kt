package nichrosia.common.config

@Suppress("UNCHECKED_CAST")
fun <C : Config, T : Configured<C, T>> Configured<C, T>.configure(configurator: C.() -> Unit): T {
    return apply { config.apply(configurator) } as T
}