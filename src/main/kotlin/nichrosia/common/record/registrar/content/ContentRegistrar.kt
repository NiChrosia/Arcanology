package nichrosia.common.record.registrar.content

import nichrosia.common.identity.ID
import nichrosia.common.record.member.MemberList
import nichrosia.common.record.member.RegistrarMember
import nichrosia.common.record.registry.bi.BiRegistrar
import nichrosia.common.record.registry.content.ContentRegistry

/** A registrar class for registering content under the given location. */
interface ContentRegistrar<T> : BiRegistrar<ID, T> {
    override val registry: ContentRegistry<T>
    val members: MemberList<T>

    fun register() {
        members.members.forEach(RegistrarMember<T, *>::content::get)
    }

    fun publish() {
        registry.catalog.forEach(this::publish)
    }

    // delegated from MemberList
    fun <E : T, M : RegistrarMember<T, E>> join(member: M) = members.join(member)
    fun locate(location: ID) = members.locate(location)

    fun <E : T> memberOf(location: ID, provider: (ID) -> E): RegistrarMember<T, E> {
        return RegistrarMember(location, provider, this).also(this::join)
    }

    open class Basic<T> : ContentRegistrar<T> {
        override val registry: ContentRegistry<T> = ContentRegistry.Basic()
        override val members: MemberList<T> = MemberList.Basic()
    }
}