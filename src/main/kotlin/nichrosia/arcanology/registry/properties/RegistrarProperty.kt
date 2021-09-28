package nichrosia.arcanology.registry.properties

import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.Registrar
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

open class RegistrarProperty<R, T : R>(val registrar: Registrar<R>, val ID: Identifier, val initializer: (String) -> T) : ReadOnlyProperty<Any?, T>, PropertyDelegateProvider<Any?, RegistrarProperty<R, T>> {
    var isCreated = false
    var isRegistered = false

    constructor(registrar: Registrar<R>, ID: String, initializer: (String) -> T) : this(registrar, Arcanology.idOf(ID), initializer)

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        registrar.apply {
            if (!containsKey(ID) || !isCreated) {
                create(ID, initializer(ID.path))
            }

            isCreated = containsKey(ID)
        }

        return registrar[ID] as T
    }

    override fun provideDelegate(thisRef: Any?, property: KProperty<*>): RegistrarProperty<R, T> {
        return apply {
            registrar.registrarProperties.add(this)
        }
    }

    open fun create(thisRef: Registrar<R>) {
        thisRef.apply {
            if (!containsKey(ID)) {
                create(ID, initializer(ID.path))
            }

            isCreated = containsKey(ID)
        }
    }
}