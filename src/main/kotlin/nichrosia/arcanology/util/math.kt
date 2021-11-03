package nichrosia.arcanology.util

import kotlin.math.pow

/** Clamp this [Float] to the minimum and maximum constraints. */
fun clamp(value: Float, min: Float = 0f, max: Float = value): Float {
    return when {
        value.isNaN() -> min
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

/** Utility function used in [thousandsNumeralMap] to make it easier to read. */
fun Long.pow(n: Int) = toDouble().pow(n).toLong()