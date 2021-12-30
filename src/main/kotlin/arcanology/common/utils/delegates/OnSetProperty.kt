package arcanology.common.utils.delegates

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/** A property that calls a given set callback when setting. Typically used for dependent properties. */
open class OnSetProperty<V>(initial: V, val onSet: (V) -> Unit) : ReadWriteProperty<Any?, V> {
    protected var value = initial

    override fun getValue(thisRef: Any?, property: KProperty<*>): V {
        return value
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: V) {
        this.value = value.also(onSet)
    }
}