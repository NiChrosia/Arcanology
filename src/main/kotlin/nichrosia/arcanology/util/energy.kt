@file:Suppress("UnstableApiUsage", "deprecation")

package nichrosia.arcanology.util

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.minecraft.item.ItemStack
import team.reborn.energy.api.EnergyStorage
import kotlin.math.pow

/** The formatted version of this [Number], utilizing Roman numerals. */
val Number.formatted: String
    get() {
        var value = toFloat()

        thousands.forEach {
            if (value >= it) value /= it
        }

        return "%.1f".format(value) + asNumeral
    }

/** This number as a numeral, e.g. 1000 -> K, 1000000 -> M, etc. */
val Number.asNumeral: String
    get() = thousandsNumeralMap.getClosestIncrement(toLong())

/** Get the [EnergyStorage] for this [ItemStack] in the energy API. */
fun ItemStack.getEnergyStorage(context: ContainerItemContext = ContainerItemContext.withInitial(this)): EnergyStorage? {
    return EnergyStorage.ITEM.find(this, context)
}

/** A list of all the powers of 1000. */
private val thousands = (1..10).map { 1000f.pow(it) }.reversed()

/** A map of powers of 1000s to Roman numerals. */
private val thousandsNumeralMap = mapOf(
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