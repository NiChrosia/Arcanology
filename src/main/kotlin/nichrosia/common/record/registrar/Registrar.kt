package nichrosia.common.record.registrar

import nichrosia.common.record.registry.Registry

/** A base registrar class for registering any type of content.
 * Content is loaded in the following steps:
 * 1. The registrars themselves are initialized.
 * 2. The content within the registrars is initialized (this is a separate step to allow registrar interop.)
 * 3. The content within the registrars is registered to external registries. */
interface Registrar<K, V> {
    val registry: Registry<K, V>

    /** Register the content object to the registry, but do not publish it externally. */
    fun <E : V> register(key: K, value: E) = registry.record(key, value)

    /** Publish the given object to external registries, and optionally register it if it does not already exist. */
    fun <E : V> publish(key: K, value: E) = value

    fun find(key: K) = registry.find(key)

    fun find(key: K, fallback: V) = registry.find(key, fallback)

    companion object
}