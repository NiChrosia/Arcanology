package nichrosia.arcanology.util

import nichrosia.arcanology.type.math.Vec2
import kotlin.math.PI
import kotlin.math.pow

private fun clamp(value: Double, min: Double = 0.0, max: Double = value): Double {
    return when {
        value < min -> min
        value > max -> max
        else -> value
    }
}

private fun clamp(value: Int, min: Int = 0, max: Int = value): Int {
    return when {
        value < min -> min
        value > max -> max
        else -> value
    }
}

private fun clamp(value: Float, min: Float = 0f, max: Float = value): Float {
    return when {
        value < min -> min
        value > max -> max
        else -> value
    }
}

fun Iterable<Float>.product(): Float {
    var product = 1f

    for (i in this) {
        product *= i
    }

    return product
}

fun Int.subtractToMin(amount: Int, min: Int = 0): Int {
    return (this - amount).clamp(min)
}

@JvmName("clampExtension")
fun Double.clamp(min: Double = 0.0, max: Double = this): Double {
    return clamp(this, min, max)
}

@JvmName("clampExtension")
fun Int.clamp(min: Int = 0, max: Int = this): Int {
    return clamp(this, min, max)
}

@JvmName("clampExtension")
fun Float.clamp(min: Float = 0f, max: Float = this): Float {
    return clamp(this, min, max)
}

fun hexagon(origin: Vec2, distance: Float): Array<Vec2> {
    return (0..5).map {
        origin + Vec2().trns((it + 1) * 60f, distance)
    }.toTypedArray()
}

infix fun Double.pow(n: Int) = pow(n)

const val degreesToRadians = PI.toFloat() / 180

private val Boolean.asBinaryInt: Int
    get() {
        return when(this) {
            true -> 1
            false -> 0
        }
    }

private val Int.asBoolean: Boolean
    get() {
        return when(this) {
            0 -> false
            1 -> true
            else -> throw IllegalStateException("Cannot convert a non-binary integer to a boolean.")
        }
    }

open class BinaryInt(binaryValue: Int) {
    constructor(boolean: Boolean) : this(boolean.asBinaryInt)

    var asBoolean: Boolean
        get() = asInt.asBoolean
        set(value) {
            asInt = value.asBinaryInt
        }

    var asInt: Int = binaryValue
        set(value) {
            if (value == 0 || value == 1) field = value
        }
}