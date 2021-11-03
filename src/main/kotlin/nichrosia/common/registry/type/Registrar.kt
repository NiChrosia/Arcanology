package nichrosia.common.registry.type

import nichrosia.common.registry.type.registry.Registry

/** A base registrar class for registering any type of content. */
interface Registrar<I, O> {
    val registry: Registry<I, O>
    
    fun <E : O> register(input: I, output: E) = registry.register(input, output)
    fun <E : O> identify(output: E) = registry.identify(output)
    fun find(input: I) = registry.find(input)

    fun register()

    companion object
}