package nichrosia.arcanology.util

import dev.technici4n.fasttransferlib.api.energy.EnergyApi
import dev.technici4n.fasttransferlib.api.energy.EnergyIo
import net.minecraft.item.ItemStack

val Double.formatted: String
    get() {
        val formatting = when {
            this >= 1_000_000_000_000 -> this / 1_000_000_000_000
            this >= 1_000_000_000 -> this / 1_000_000_000
            this >= 1_000_000 -> this / 1_000_000
            this >= 1_000 -> this / 1_000
            else -> this
        }

        return "%.1f".format(formatting) + asNumeral
    }

val Double.asNumeral: String
    get() = thousandsNumeralMap.getClosest(this)

val ItemStack.energyIO: EnergyIo?
    get() = EnergyApi.ITEM.find(this, null)

private val thousandsNumeralMap = mapOf(
    1000.0 pow 1 to "k",
    1000.0 pow 2 to "M",
    1000.0 pow 3 to "B",
    1000.0 pow 4 to "T"
)