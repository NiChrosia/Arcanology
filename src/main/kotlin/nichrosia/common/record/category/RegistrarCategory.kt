package nichrosia.common.record.category

import nichrosia.common.identity.ID
import nichrosia.common.record.registrar.Registrar
import nichrosia.common.record.registrar.content.ContentRegistrar

/** A category of registrars, typically has an extension property of Registrar.Companion that mirrors this. */
open class RegistrarCategory(open val ID: ID) {
    open val categories = mutableListOf<Lazy<RegistrarCategory>>()
    open val registrars = mutableListOf<Lazy<Registrar<*, *>>>()

    open fun <C : RegistrarCategory> categoryOf(provider: () -> C) = lazy(provider).also(categories::add)
    open fun <K, V, R : Registrar<K, V>> registrarOf(provider: () -> R) = lazy(provider).also(registrars::add)

    open fun initialize() {
        register()

        publish()
    }

    open fun register() {
        categories.forEach {
            it.value.register()
        }

        registrars.forEach {
            (it.value as? ContentRegistrar<*>)?.register()
        }
    }

    open fun publish() {
        categories.forEach {
            it.value.publish()
        }

        registrars.forEach {
            (it.value as? ContentRegistrar<*>)?.publish()
        }
    }
}