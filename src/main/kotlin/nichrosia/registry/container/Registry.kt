package nichrosia.registry.container

import nichrosia.common.identity.ID

open class Registry<T> {
    open val content: MutableMap<ID, T> = mutableMapOf()

    open fun <E : T> register(location: ID, value: E): E {
        return value.also {
            content[location] = it
        }
    }

    open fun identify(value: T): ID? {
        val reversed = mutableMapOf(*content.map { it.value to it.key }.toTypedArray())

        return reversed[value]
    }

    open fun find(location: ID): T? {
        return content[location]
    }
}