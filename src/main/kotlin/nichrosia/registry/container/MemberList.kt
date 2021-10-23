package nichrosia.registry.container

import nichrosia.registry.delegates.RegistrarMember

open class MemberList<T> {
    open val members: MutableList<RegistrarMember<T, *>> = mutableListOf()

    open fun <E : T> join(member: RegistrarMember<T, E>): RegistrarMember<T, E> {
        return member.also {
            members.add(it)
        }
    }

    open fun register() {
        members.forEach {
            if (!it.registered) it.register()
        }
    }
}