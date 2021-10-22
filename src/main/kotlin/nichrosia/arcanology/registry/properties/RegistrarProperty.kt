package nichrosia.arcanology.registry.properties

import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.Registrar
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST", "LeakingThis")
open class RegistrarProperty<R, T : R>(
    val ID: Identifier,
    val initializer: (Identifier) -> T
) : ReadOnlyProperty<Registrar<R>, T>, PropertyDelegateProvider<Registrar<R>, RegistrarProperty<R, T>> {
    private var isCreated = false

    constructor(ID: String, initializer: (Identifier) -> T) : this(Arcanology.idOf(ID), initializer)

    override fun getValue(thisRef: Registrar<R>, property: KProperty<*>): T {
        thisRef.apply {
            if (!containsKey(ID) || !isCreated) {
                create(ID, initializer(ID))
            }

            isCreated = containsKey(ID)
        }

        return thisRef[ID] as T
    }

    override fun provideDelegate(thisRef: Registrar<R>, property: KProperty<*>): RegistrarProperty<R, T> {
        return apply {
            thisRef.registrarProperties.add(this)
        }
    }

    open fun create(thisRef: Registrar<R>) {
        thisRef.apply {
            if (!containsKey(ID)) {
                create(ID, initializer(ID))
            }

            isCreated = containsKey(ID)
        }
    }
}