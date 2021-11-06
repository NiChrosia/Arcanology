package nichrosia.common.record.category

import nichrosia.common.record.registrar.Registrar
import nichrosia.common.record.registrar.content.ContentRegistrar

open class SubRegistrar<K, V, R : Registrar<K, V>>(provider: () -> R) : Lazy<R> by lazy(provider) {
    open fun register() {
        value.apply {
            if (this is ContentRegistrar<*>) register()
        }
    }

    open fun publish() {
        value.apply {
            if (this is ContentRegistrar<*>) publish()
        }
    }
}