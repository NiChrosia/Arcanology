package nichrosia.common.record.registry

interface Registry<K, V> {
    val catalog: MutableMap<K, V>

    fun <E : V> record(key: K, value: E): E {
        return value.also {
            catalog[key] = it
        }
    }

    fun find(key: K): V? {
        return catalog[key]
    }

    fun find(key: K, fallback: V): V {
        return find(key) ?: record(key, fallback)
    }

    open class Basic<K, V> : Registry<K, V> {
        override val catalog: MutableMap<K, V> = mutableMapOf()
    }
}