package nichrosia.arcanology.math

object Math {
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
}