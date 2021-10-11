@file:Suppress("UnstableApiUsage", "deprecation")

package nichrosia.arcanology.util

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.minecraft.item.ItemStack
import team.reborn.energy.api.EnergyStorage

val Long.formatted: String
    get() {
        val formatting = when {
            this >= 1_000_000_000_000 -> this / 1_000_000_000_000f
            this >= 1_000_000_000 -> this / 1_000_000_000f
            this >= 1_000_000 -> this / 1_000_000f
            this >= 1_000 -> this / 1_000f
            else -> this.toFloat()
        }

        return "%.1f".format(formatting) + asNumeral
    }

val Long.asNumeral: String
    get() = thousandsNumeralMap.getClosestIncrement(this)

fun ItemStack.getEnergyStorage(context: ContainerItemContext = ContainerItemContext.withInitial(this)): EnergyStorage? {
    return EnergyStorage.ITEM.find(this, context)
}

private val thousandsNumeralMap = mapOf(
    0L to "",
    1000L pow 1 to "k",
    1000L pow 2 to "M",
    1000L pow 3 to "B",
    1000L pow 4 to "T",
    1000L pow 5 to "q",
    1000L pow 6 to "Q",
    1000L pow 7 to "s",
    1000L pow 8 to "S",
    1000L pow 9 to "O",
    1000L pow 10 to "N",
    1000L pow 11 to "D"
)