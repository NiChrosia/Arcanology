package nichrosia.common.record.registry.content

import nichrosia.common.identity.ID
import nichrosia.common.record.registrar.bi.BiRegistry

interface ContentRegistry<T> : BiRegistry<ID, T> {
    open class Basic<T> : ContentRegistry<T> {
        override val catalog: MutableMap<ID, T> = mutableMapOf()
        override val reversed: MutableMap<T, ID> = mutableMapOf()
    }
}