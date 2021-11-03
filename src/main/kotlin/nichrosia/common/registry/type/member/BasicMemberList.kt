package nichrosia.common.registry.type.member

open class BasicMemberList<T> : MemberList<T> {
    override val members: MutableList<RegistrarMember<T, *, *>> = mutableListOf()
}