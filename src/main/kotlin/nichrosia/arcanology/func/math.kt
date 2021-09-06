package nichrosia.arcanology.func

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

private val Boolean.asBinaryInt: Int
    get() {
        return when(this) {
            true -> 1
            false -> 0
        }
    }

private val Int.asBoolean: Boolean
    get() {
        if (equals(0)) return false
        if (equals(1)) return true

        throw IllegalStateException("Cannot convert a non-binary integer to a boolean.")
    }

open class BinaryInt(binaryValue: Int) {
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