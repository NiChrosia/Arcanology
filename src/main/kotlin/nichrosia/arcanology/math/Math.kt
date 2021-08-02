package nichrosia.arcanology.math

object Math {
    private fun clamp(value: Double, min: Double = 0.0, max: Double = value): Double {
        return when {
            value < min -> min
            value > max -> max
            else -> value
        }
    }

    @JvmName("clamp_extension")
    fun Double.clamp(min: Double = 0.0, max: Double = this): Double {
        return clamp(this, min, max)
    }
}