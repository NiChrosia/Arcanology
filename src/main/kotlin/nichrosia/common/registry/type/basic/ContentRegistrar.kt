package nichrosia.common.registry.type.basic

import nichrosia.common.config.Config
import nichrosia.common.identity.ID
import nichrosia.common.registry.type.Registrar
import nichrosia.common.registry.type.member.BasicMemberList
import nichrosia.common.registry.type.member.RegistrarMember
import nichrosia.common.registry.type.registry.ContentRegistry

/** A registrar class for registering content under the given location. */
interface ContentRegistrar<T> : Registrar<ID, T> {
    override val registry: ContentRegistry<T>
    val members: BasicMemberList<T>

    override fun register() {
        members.register()
    }

    // delegated from MemberList
    fun <E : T, C : Config, M : RegistrarMember<T, E, C>> join(member: M) = members.join(member)
    fun locate(location: ID) = members.locate(location)

    fun <E : T> memberOf(location: ID, provider: (ID) -> E): RegistrarMember<T, E, Config> {
        return RegistrarMember<T, E, Config>(location, provider, this).also(this::join)
    }
}