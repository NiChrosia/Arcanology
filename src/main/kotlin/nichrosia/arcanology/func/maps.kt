package nichrosia.arcanology.func

import kotlin.math.abs

/** Gets the closest value to the given value
 * @param value the parameter to get the closest value to
 * @return The closest value */
internal fun <V> Map<Double, V>.getClosest(value: Double): V {
    return this[keys.toTypedArray().sort { abs(it - value).toInt() }.first()]!!
}