package nichrosia.common.record.category

import nichrosia.common.identity.ID
import nichrosia.common.record.registrar.Registrar

/** A category of registrars, typically has an extension property of Registrar.Companion that mirrors this. */
open class RegistrarCategory(open val ID: ID) {
    open val categories = mutableListOf<SubCategory<*>>()
    open val registrars = mutableListOf<SubRegistrar<*, *, *>>()

    open fun <K, V, R : Registrar<K, V>> registrarOf(provider: () -> R) = SubRegistrar(provider).also(registrars::add)
    open fun <C : RegistrarCategory> categoryOf(provider: () -> C) = SubCategory(provider).also(categories::add)

    open fun initialize() {
        register()
        publish()
    }

    open fun register() {
        categories.forEach(SubCategory<*>::register)
        registrars.forEach(SubRegistrar<*, *, *>::register)
    }

    open fun publish() {
        categories.forEach(SubCategory<*>::publish)
        registrars.forEach(SubRegistrar<*, *, *>::publish)
    }
}