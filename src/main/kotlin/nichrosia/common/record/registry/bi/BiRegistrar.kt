package nichrosia.common.record.registry.bi

import nichrosia.common.record.registrar.Registrar
import nichrosia.common.record.registrar.bi.BiRegistry

interface BiRegistrar<K, V> : Registrar<K, V> {
    override val registry: BiRegistry<K, V>

    fun <E : V> identify(value: E) = registry.identify(value)
}