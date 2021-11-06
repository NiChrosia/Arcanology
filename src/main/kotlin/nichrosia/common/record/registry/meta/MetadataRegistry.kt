package nichrosia.common.record.registry.meta

import nichrosia.common.record.registry.Registry
import nichrosia.common.record.registry.meta.key.MetadataKey

/** A registry implementation that contains map of keys with metadata & values. */
interface MetadataRegistry<K, M, V> : Registry<MetadataKey<K, M>, V> {
    fun <E : V> add(key: K, metadata: M, value: E): E {
        return value.also {
            val metadataKey = keyOf(key, metadata)

            catalog[metadataKey] = it
        }
    }

    fun keyOf(key: K, metadata: M): MetadataKey<K, M> {
        return MetadataKey(key, metadata)
    }

    open class Basic<K, M, V> : MetadataRegistry<K, M, V> {
        override val catalog: MutableMap<MetadataKey<K, M>, V> = mutableMapOf()
    }
}