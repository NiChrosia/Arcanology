package nichrosia.arcanology.registry.properties

import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.Registrar
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/** RegistrarProperty for pre-existing registrars. */
open class ExternalRegistrarProperty<R, T : R>(
    val registrar: Registrar<R>,
    val ID: Identifier,
    /** Whether to call the create function using the specified [initializer]. */
    val createContent: Boolean = true,
    val initializer: (Identifier) -> T
) : ReadOnlyProperty<Any?, T>, PropertyDelegateProvider<Any?, ExternalRegistrarProperty<R, T>> {
    var isCreated = false
    var isRegistered = false

    constructor(registrar: Registrar<R>, ID: String, createContent: Boolean = true, initializer: (Identifier) -> T) : this(registrar, Arcanology.idOf(ID), createContent, initializer)

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        registrar.apply {
            if (!containsKey(ID) || !isCreated) {
                val content = initializer(ID)

                if (createContent) create(ID, content)
            }

            isCreated = containsKey(ID)
        }

        return registrar[ID] as T
    }

    override fun provideDelegate(thisRef: Any?, property: KProperty<*>): ExternalRegistrarProperty<R, T> {
        return apply { registrar.externalRegistrarProperties.add(this) }
    }

    open fun create(thisRef: Registrar<R>) {
        thisRef.apply {
            if (!containsKey(ID)) {
                val content = initializer(ID)

                if (createContent) create(ID, content)
            }

            isCreated = containsKey(ID)
        }
    }
}