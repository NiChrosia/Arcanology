package nichrosia.common.registry.type.member

import nichrosia.common.config.Config
import nichrosia.common.identity.ID

interface MemberList<T> {
    val members: MutableList<RegistrarMember<T, *, *>>

    fun <E : T, C : Config> join(member: RegistrarMember<T, E, C>): RegistrarMember<T, E, C> {
        return member.also(members::add)
    }

    fun locate(location: ID): RegistrarMember<T, *, *>? {
        return members.firstOrNull { it.location == location }
    }

    fun register() {
        members.forEach {
            if (!it.registered) it.register()
        }
    }
}