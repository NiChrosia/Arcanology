package nichrosia.common.record.member

import nichrosia.common.identity.ID
import nichrosia.common.record.registrar.content.ContentRegistrar
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

open class RegistrarMember<R, T : R> internal constructor(
    val location: ID,
    val provider: (ID) -> T,
    val reference: ContentRegistrar<R>
) : ReadOnlyProperty<Any?, T> {
    val content by lazy {
        reference.register(location, provider(location))
    }

    init {
        reference.join(this)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return content
    }
}