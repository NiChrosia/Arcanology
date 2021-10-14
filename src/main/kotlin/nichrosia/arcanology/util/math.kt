package nichrosia.arcanology.util

import nichrosia.arcanology.type.math.Vec2
import kotlin.math.pow

/** Clamp this [Double] to the minimum and maximum constraints. */
fun clamp(value: Double, min: Double = 0.0, max: Double = value): Double {
    return when {
        value < min -> min
        value > max -> max
        else -> value
    }
}

/** Clamp this [Int] to the minimum and maximum constraints. */
fun clamp(value: Int, min: Int = 0, max: Int = value): Int {
    return when {
        value < min -> min
        value > max -> max
        else -> value
    }
}

/** Create a hexagon of [Vec2]s using an origin point and the distance each point should be from it. */
fun hexagon(origin: Vec2, distance: Float): Array<Vec2> {
    return (0..5).map {
        origin + Vec2().trns((it + 1) * 60f, distance)
    }.toTypedArray()
}

/** Utility function used in [thousandsNumeralMap] to make it easier to read. */
fun Long.pow(n: Int) = toDouble().pow(n).toLong()

/** Convert this boolean to a binary int, e.g. true -> 1, false -> 0. */
val Boolean.asBinaryInt: Int
    get() {
        return when(this) {
            true -> 1
            false -> 0
        }
    }

/** Convert this int to a boolean
 * @throws IllegalArgumentException if this is not a binary integer. */
val Int.asBoolean: Boolean
    get() {
        return when(this) {
            0 -> false
            1 -> true
            else -> throw IllegalArgumentException("This integer is not binary.")
        }
    }