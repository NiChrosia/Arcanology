package nichrosia.common.record.registrar.bi

import nichrosia.common.record.registry.Registry

interface BiRegistry<K, V> : Registry<K, V> {
    val reversed: MutableMap<V, K>

    override fun <E : V> record(key: K, value: E): E {
        return value.also {
            catalog[key] = it
            reversed[it] = key
        }
    }

    fun <E : V> identify(value: E): K? {
        return reversed[value]
    }
}