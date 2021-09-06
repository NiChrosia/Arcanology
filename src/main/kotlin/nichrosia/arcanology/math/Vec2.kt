package nichrosia.arcanology.math

import nichrosia.arcanology.func.degreesToRadians
import kotlin.math.cos
import kotlin.math.sin

@Suppress("MemberVisibilityCanBePrivate")
data class Vec2(var x: Float, var y: Float) {
    constructor() : this(0f, 0f)
    constructor(x: Int, y: Int) : this(x.toFloat(), y.toFloat())

    fun set(x: Float, y: Float): Vec2 {
        this.x = x
        this.y = y

        return this
    }

    fun trns(angle: Float, amount: Float): Vec2 {
        return set(amount, 0f).rotate(angle)
    }

    fun rotate(degrees: Float): Vec2 {
        return rotateRad(degrees * degreesToRadians)
    }

    fun rotateRad(radians: Float): Vec2 {
        val cos = cos(radians)
        val sin = sin(radians)

        val newX = x * cos - y * sin
        val newY = x * sin + y * cos

        x = newX
        y = newY

        return this
    }

    operator fun plus(other: Vec2): Vec2 {
        return Vec2(x + other.x, y + other.y)
    }
}