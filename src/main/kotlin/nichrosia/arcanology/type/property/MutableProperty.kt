package nichrosia.arcanology.type.property

import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty0

open class MutableProperty<B, F>(
    val getter: () -> B,
    val setter: (B) -> Unit,
    val toFront: (B) -> F,
    val toBack: (F) -> B
) {
    constructor(property: KMutableProperty0<B>, toFront: (B) -> F, toBack: (F) -> B) : this(property::get, property::set, toFront, toBack)
    constructor(property: KProperty0<B>, toFront: (B) -> F, toBack: (F) -> B) : this(property::get, {}, toFront, toBack)

    operator fun invoke() = toFront(getter())
    operator fun invoke(value: F) = setter(toBack(value))
}