package nichrosia.common.registry.type.member

import nichrosia.common.config.Config
import nichrosia.common.config.Configured
import nichrosia.common.identity.ID
import nichrosia.common.registry.type.Registrar
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST", "LeakingThis")
open class RegistrarMember<R, T : R, C : Config> internal constructor(
    val location: ID,
    val provider: (ID) -> T,
    val reference: Registrar<ID, R>
) : ReadOnlyProperty<Any?, T>, Configured<C> {
    override val config: C
        get() = throw IllegalStateException("Attempted to access config in raw registrar member.")

    val content by lazy { provider(location) }

    val registered: Boolean
        get() = reference.find(location) != null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return content.also {
            if (!registered) register()
        }
    }
    
    open fun register() {
        reference.register(location, content)
    }
}