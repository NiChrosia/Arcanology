package nichrosia.registry.category

import nichrosia.common.identity.ID
import nichrosia.registry.Registrar
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

/** A category of registrars, typically has an extension property of Registrar.Companion that mirrors this. */
open class RegistrarCategory(open val ID: ID) {
    open val registrars: List<Registrar<*>>
        get() = this::class.declaredMemberProperties
            .filterIsInstance<KProperty1<RegistrarCategory, *>>()
            .map { it.get(this) }
            .filterIsInstance<Registrar<*>>()

    open fun register() {
        registrars.forEach(Registrar<*>::registerMembers)
    }
}