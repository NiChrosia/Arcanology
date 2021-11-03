package nichrosia.common.registry.type.lazy

import nichrosia.common.registry.type.Registrar
import nichrosia.common.registry.type.registry.BasicRegistry

/** A registrar class for registering dynamically converted content from I to O. */
interface LazyRegistrar<I, O> : Registrar<I, O> {
    override val registry: BasicRegistry<I, O>

    override fun register() {
        // do nothing, as content is lazily generated from the convert function
    }

    override fun find(input: I): O? {
        val registryResult = registry.find(input)

        return registryResult ?: run {
            val converted = convert(input)

            converted?.let {
                register(input, it)
            }
        }
    }

    fun convert(input: I): O?
}