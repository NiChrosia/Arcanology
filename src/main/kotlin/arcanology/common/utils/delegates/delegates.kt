package arcanology.common.utils.delegates

/** Returns a new property with the given setter callback. */
fun <V> onSet(initial: V, onSet: (V) -> Unit): OnSetProperty<V> {
    return OnSetProperty(initial, onSet)
}