package nichrosia.registry

import nichrosia.common.identity.ID
import nichrosia.registry.container.MemberList
import nichrosia.registry.container.Registry
import nichrosia.registry.delegates.RegistrarMember

/** A base registrar class for registering content. */
@Suppress("UNCHECKED_CAST")
interface Registrar<T> {
    val registry: Registry<T>
    val members: MemberList<T>

    /** Register all of the content declared within its members. */
    fun registerMembers() {
        members.register()
    }

    fun <E : T> register(location: ID, value: E) = registry.register(location, value)
    fun identify(value: T) = registry.identify(value)
    fun find(location: ID) = registry.find(location)

    fun <E : T> join(member: RegistrarMember<T, E>) = members.join(member)

    fun <E : T> memberOf(location: ID, provider: (ID) -> E): RegistrarMember<T, E> {
        return RegistrarMember(location, provider, this).also(this::join)
    }

    companion object
}