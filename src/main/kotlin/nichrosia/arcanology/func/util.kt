@file:Suppress("unused")

package nichrosia.arcanology.func

import dev.technici4n.fasttransferlib.api.energy.EnergyApi
import dev.technici4n.fasttransferlib.api.energy.EnergyIo
import net.minecraft.item.ItemStack
import java.util.*
import kotlin.reflect.KMutableProperty0

private fun getEnergyString(energy: Double): String {
    return when {
        energy >= 1_000_000_000_000 -> "${"%.1f".format(energy / 1_000_000_000_000)}T"
        energy >= 1_000_000_000 -> "${"%.1f".format(energy / 1_000_000_000)}B"
        energy >= 1_000_000 -> "${"%.1f".format(energy / 1_000_000)}M"
        energy >= 1_000 -> "${"%.1f".format(energy / 1_000)}k"
        else -> "%.1f".format(energy)
    }
}

/** Decrements the count, and if it's one, set to empty
 * @return A new [ItemStack] with the changes */
fun ItemStack.decrement(): ItemStack {
    return if (count > 1) apply { count = count.subtractToMin(1) } else ItemStack.EMPTY
}

fun ItemStack.mergeCount(other: ItemStack): ItemStack {
    return ItemStack(item, count + other.count)
}

fun KMutableProperty0<ItemStack>.decrement() {
    set(get().decrement())
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

internal val Int.asBoolean: Boolean
    get() {
        if (equals(0)) return false
        if (equals(1)) return true

        throw IllegalStateException("Cannot convert a non-binary integer to a boolean.")
    }