package nichrosia.arcanology.type.properties

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

open class ConditionedProperty<T>(val condition: () -> Boolean, val falseSettings: T, val trueSettings: T, val additionalConfiguration: (T) -> T = { it }) : ReadOnlyProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return additionalConfiguration(if (condition()) trueSettings else falseSettings)
    }
}