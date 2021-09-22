package nichrosia.arcanology.type.content

interface RegisterableContent<T, P> {
    val registry: (P) -> T

    fun register(parameter: P): T {
        return registry(parameter)
    }
}