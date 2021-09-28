package nichrosia.arcanology.util

import net.minecraft.item.ItemStack
import java.util.*
import kotlin.reflect.KMutableProperty0

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

/** Performs the given map operation using elements from both arrays.
 * @throws IllegalStateException if the two arrays are not equal size */
inline fun <A, B, reified C> Array<A>.dualMap(other: Array<B>, consumer: (A, B) -> C): Array<C> {
    arraysSameSize(this, other)

    return mapIndexed { i, e -> consumer(e, other[i]) }.toTypedArray()
}

/** Merges two equal size arrays into an array of pairs.
 * @throws IllegalStateException if the two arrays are not equal size */
fun <A, B> Array<A>.merge(other: Array<B>): Array<Pair<A, B>> {
    arraysSameSize(this, other)

    return dualMap(other) { a, b -> a to b }
}

inline fun <reified T> Array<T>.sort(crossinline predicate: (T) -> Int): Array<T> {
    Arrays.sort(this) { a, b ->
        predicate(a).compareTo(predicate(b))
    }

    return this
}

/** Checks if both arrays are the same size.
 * @throws IllegalStateException if the two arrays are not equal size */
fun arraysSameSize(a: Array<*>, b: Array<*>) {
    if (a.size != b.size) throw IllegalStateException("Both arrays must be the same size.")
}

fun <T> MutableList<T>.addAll(vararg elements: T) = addAll(elements)