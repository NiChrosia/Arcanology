package nichrosia.registry.delegates

import nichrosia.common.identity.ID
import nichrosia.registry.Registrar
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST", "LeakingThis")
open class RegistrarMember<R, T : R> internal constructor(
    val ID: ID,
    val provider: (ID) -> T,
    val reference: Registrar<R>
) : ReadOnlyProperty<Any?, T> {
    val content by lazy { provider(ID) }

    val registered: Boolean
        get() = reference.find(ID) != null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return content.also {
            if (!registered) register()
        }
    }
    
    open fun register() {
        reference.register(ID, content)
    }
}