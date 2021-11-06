package nichrosia.common.record.member

import nichrosia.common.identity.ID

interface MemberList<T> {
    val members: MutableList<RegistrarMember<T, *>>

    fun <E : T> join(member: RegistrarMember<T, E>): RegistrarMember<T, E> {
        return member.also(members::add)
    }

    fun locate(location: ID): RegistrarMember<T, *>? {
        return members.firstOrNull { it.location == location }
    }

    open class Basic<T> : MemberList<T> {
        override val members: MutableList<RegistrarMember<T, *>> = mutableListOf()
    }
}