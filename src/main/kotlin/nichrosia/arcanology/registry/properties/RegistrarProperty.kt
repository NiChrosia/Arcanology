package nichrosia.arcanology.registry.properties

import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.Registrar
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

open class RegistrarProperty<R, T : R>(
    val registrar: Registrar<R>,
    val ID: Identifier,
    /** Whether to call the create function using the specified [initializer]. */
    val createContent: Boolean = true,
    val initializer: (String) -> T
) : ReadOnlyProperty<Any?, T>, PropertyDelegateProvider<Any?, RegistrarProperty<R, T>> {
    var isCreated = false
    var isRegistered = false

    constructor(registrar: Registrar<R>, ID: String, createContent: Boolean = true, initializer: (String) -> T) : this(registrar, Arcanology.idOf(ID), createContent, initializer)

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        registrar.apply {
            if (!containsKey(ID) || !isCreated) {
                val content = initializer(ID.path)

                if (createContent) create(ID, content)
            }

            isCreated = containsKey(ID)
        }

        return registrar[ID] as T
    }

    override fun provideDelegate(thisRef: Any?, property: KProperty<*>): RegistrarProperty<R, T> {
        return apply { registrar.registrarProperties.add(this) }
    }

    open fun create(thisRef: Registrar<R>) {
        thisRef.apply {
            if (!containsKey(ID)) {
                val content = initializer(ID.path)

                if (createContent) create(ID, content)
            }

            isCreated = containsKey(ID)
        }
    }
}