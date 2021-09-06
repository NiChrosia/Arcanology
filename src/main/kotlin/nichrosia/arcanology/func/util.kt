package nichrosia.arcanology.func

import dev.technici4n.fasttransferlib.api.energy.EnergyApi
import dev.technici4n.fasttransferlib.api.energy.EnergyIo
import net.minecraft.item.ItemStack
import java.util.*
import kotlin.Comparator
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.reflect.KMutableProperty0
import net.minecraft.client.MinecraftClient as RealMinecraftClient

/** Decrements the count, and if it's one, set to empty
 * @return A new [ItemStack] with the changes */
internal fun ItemStack.decrement(): ItemStack {
    return if (count > 1) apply { count = count.subtractToMin(1) } else ItemStack.EMPTY
}

internal fun ItemStack.mergeCount(other: ItemStack): ItemStack {
    return ItemStack(item, count + other.count)
}

internal fun KMutableProperty0<ItemStack>.decrement() {
    set(get().decrement())
}

/** Checks if both arrays are the same size.
 * @throws IllegalStateException if the two arrays are not equal size */
internal fun arraysSameSize(a: Array<*>, b: Array<*>) {
    if (a.size != b.size) throw IllegalStateException("Both arrays must be the same size.")
}

/** Performs the given map operation using elements from both arrays.
 * @throws IllegalStateException if the two arrays are not equal size */
internal inline fun <A, B, reified C> Array<A>.dualMap(other: Array<B>, consumer: (A, B) -> C): Array<C> {
    arraysSameSize(this, other)

    return mapIndexed { i, e -> consumer(e, other[i]) }.toTypedArray()
}

/** Merges two equal size arrays into an array of pairs.
 * @throws IllegalStateException if the two arrays are not equal size */
internal fun <A, B> Array<A>.merge(other: Array<B>): Array<Pair<A, B>> {
    arraysSameSize(this, other)

    return dualMap(other) { a, b -> a to b }
}

internal fun <T> Array<T>.sort(predicate: (T) -> Int): Array<T> {
    Arrays.sort(this) { a, b ->
        predicate(a).compareTo(predicate(b))
    }

    return this
}

/** Gets the closest value to the given value
 * @param value the parameter to get the closest value to
 * @return The closest value */
internal fun <V> Map<Double, V>.getClosest(value: Double): V {
    return this[keys.toTypedArray().sort { abs(it - value).toInt() }.first()]!!
}

internal val Double.formatted: String
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

infix fun Double.pow(n: Int) = pow(n)

private val thousandsNumeralMap = mapOf(
    1000.0 pow 1 to "k",
    1000.0 pow 2 to "M",
    1000.0 pow 3 to "B",
    1000.0 pow 4 to "T"
)

internal val Double.asNumeral: String
    get() = thousandsNumeralMap.getClosest(this)

internal val ItemStack.energyIO: EnergyIo?
    get() = EnergyApi.ITEM.find(this, null)

internal val <T> Optional<T>.asNullable: T?
    get() = if (isPresent) get() else null

internal val Boolean.asBinaryInt: Int
    get() {
        return when(this) {
            true -> 1
            false -> 0
        }
    }

internal val Int.asBoolean: Boolean
    get() {
        if (equals(0)) return false
        if (equals(1)) return true

        throw IllegalStateException("Cannot convert a non-binary integer to a boolean.")
    }

internal val minecraftClient: RealMinecraftClient
    get() = RealMinecraftClient.getInstance()