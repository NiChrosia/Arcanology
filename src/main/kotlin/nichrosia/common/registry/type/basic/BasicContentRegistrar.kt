package nichrosia.common.registry.type.basic

import nichrosia.common.registry.type.Registrar
import nichrosia.common.registry.type.member.BasicMemberList
import nichrosia.common.registry.type.registry.ContentRegistry

/** A basic [Registrar] implementation. */
abstract class BasicContentRegistrar<T> : ContentRegistrar<T> {
    override val registry: ContentRegistry<T> = ContentRegistry()
    override val members: BasicMemberList<T> = BasicMemberList()
}