package nichrosia.registry

import nichrosia.registry.container.MemberList
import nichrosia.registry.container.Registry

/** A basic [Registrar] implementation. */
abstract class BasicRegistrar<T> : Registrar<T> {
    override val registry: Registry<T> = Registry()
    override val members: MemberList<T> = MemberList()
}