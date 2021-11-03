package nichrosia.common.registry.type.registry

import nichrosia.common.identity.ID

open class ContentRegistry<T> : Registry<ID, T> {
    override val content: MutableMap<ID, T> = mutableMapOf()
    override val reversed: MutableMap<T, ID> = mutableMapOf()
}