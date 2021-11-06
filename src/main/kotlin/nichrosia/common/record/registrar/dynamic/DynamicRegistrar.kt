package nichrosia.common.record.registrar.dynamic

import nichrosia.common.record.registrar.Registrar
import nichrosia.common.record.registry.Registry

/** A registrar class for registering dynamically converted content from key to value. */
interface DynamicRegistrar<K, V> : Registrar<K, V> {
    override val registry: Registry<K, V>

    override fun find(key: K): V? {
        val registryResult = registry.find(key)

        return registryResult ?: run {
            val converted = convert(key)

            converted?.let {
                register(key, it)
            }
        }
    }

    fun convert(key: K): V?
}