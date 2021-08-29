@file:Suppress("unused")

package nichrosia.arcanology.util

import dev.technici4n.fasttransferlib.api.energy.EnergyApi
import dev.technici4n.fasttransferlib.api.energy.EnergyIo
import net.minecraft.item.ItemStack
import java.util.*

private fun getEnergyString(energy: Double): String {
    return when {
        energy >= 1_000_000_000_000 -> "${"%.1f".format(energy / 1_000_000_000_000)}T"
        energy >= 1_000_000_000 -> "${"%.1f".format(energy / 1_000_000_000)}B"
        energy >= 1_000_000 -> "${"%.1f".format(energy / 1_000_000)}M"
        energy >= 1_000 -> "${"%.1f".format(energy / 1_000)}k"
        else -> "%.1f".format(energy)
    }
}

internal val Double.formatted: String
    get() = getEnergyString(this)

internal val Float.formatted: String
    get() = getEnergyString(toDouble())

internal val Int.formatted: String
    get() = getEnergyString(toDouble())

internal val ItemStack.energyIO: EnergyIo?
    get() = EnergyApi.ITEM.find(this, null)

internal val <T> Optional<T>.asNullable: T?
    get() = if (isPresent) get() else null