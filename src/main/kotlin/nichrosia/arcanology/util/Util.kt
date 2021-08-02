package nichrosia.arcanology.util

import dev.technici4n.fasttransferlib.api.energy.EnergyApi
import dev.technici4n.fasttransferlib.api.energy.EnergyIo
import net.minecraft.item.ItemStack

internal fun getEnergyString(energy: Double): String {
    return when {
        energy >= 1000000000000 -> "${"%.1f".format(energy / 1000000000000)}T"
        energy >= 1000000000 -> "${"%.1f".format(energy / 1000000000)}B"
        energy >= 1000000 -> "${"%.1f".format(energy / 1000000)}M"
        energy >= 1000 -> "${"%.1f".format(energy / 1000)}k"
        else -> "%.1f".format(energy)
    }
}

internal fun energyOf(itemStack: ItemStack): EnergyIo? {
    return EnergyApi.ITEM.find(itemStack, null)
}