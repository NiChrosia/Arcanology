@file:Suppress("UnstableApiUsage", "deprecation")

package arcanology.util.world.energy

import arcanology.util.collections.getClosestIncrement
import arcanology.util.primitives.numbers.pow
import kotlin.math.pow

const val energyUnit = "LF"

val Number.formatted: String
    get() {
        var value = toFloat()

        thousands.forEach {
            if (value >= it) value /= it
        }

        return "%.1f".format(value) + asNumeral
    }

val Number.asNumeral: String
    get() = thousandsNumeralMap.getClosestIncrement(toLong())

val thousands = (1..10).map { 1000f.pow(it) }.reversed()

val thousandsNumeralMap = mapOf(
    0L to "",
    1000L.pow(1) to "k",
    1000L.pow(2) to "M",
    1000L.pow(3) to "B",
    1000L.pow(4) to "T",
    1000L.pow(5) to "q",
    1000L.pow(6) to "Q",
    1000L.pow(7) to "s",
    1000L.pow(8) to "S",
    1000L.pow(9) to "O",
    1000L.pow(10) to "N",
    1000L.pow(11) to "D"
)