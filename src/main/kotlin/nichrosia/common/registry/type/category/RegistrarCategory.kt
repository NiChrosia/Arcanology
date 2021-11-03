package nichrosia.common.registry.type.category

import nichrosia.common.identity.ID
import nichrosia.common.registry.type.Registrar

/** A category of registrars, typically has an extension property of Registrar.Companion that mirrors this. */
open class RegistrarCategory(open val ID: ID) {
    open val registrars = mutableListOf<CategorizedRegistrar<*, *, *>>()

    open fun <I, O, R : Registrar<I, O>> registrarOf(provider: () -> R) = CategorizedRegistrar(this, provider)

    open fun register() {
        registrars.forEach(CategorizedRegistrar<*, *, *>::register)
    }
}