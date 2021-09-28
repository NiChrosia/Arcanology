package nichrosia.arcanology.type.properties

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0

open class ConditionedProperty<T>(val condition: KProperty0<Boolean>, val falseSettings: T, val trueSettings: T) : ReadOnlyProperty<Any?, T> {
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return if (condition.get()) trueSettings else falseSettings
    }
}