package nichrosia.common.registry.type.category

import nichrosia.common.registry.type.Registrar
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

open class CategorizedRegistrar<I, O, R : Registrar<I, O>>(val reference: RegistrarCategory, provider: () -> R) : ReadOnlyProperty<Any, R>, PropertyDelegateProvider<Any, CategorizedRegistrar<I, O, R>> {
    val content by lazy(provider)

    override fun provideDelegate(thisRef: Any, property: KProperty<*>): CategorizedRegistrar<I, O, R> {
        return apply { reference.registrars.add(this) }
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): R {
        return content
    }

    open fun register() {
        content.register()
    }
}