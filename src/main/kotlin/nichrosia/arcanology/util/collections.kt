package nichrosia.arcanology.util

import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList

/** Performs the given map operation using elements from both arrays.
 * @throws IllegalStateException if the two arrays are not equal size */
inline fun <A, B, reified C> Array<A>.dualMap(other: Array<B>, consumer: (A, B) -> C): Array<C> {
    checkForSameSize(this, other)

    return mapIndexed { i, e -> consumer(e, other[i]) }.toTypedArray()
}

/** Merges two equal size arrays into an array of pairs.
 * @throws IllegalStateException if the two arrays are not equal size */
fun <A, B> Array<A>.merge(other: Array<B>): Array<Pair<A, B>> {
    checkForSameSize(this, other)

    return dualMap(other) { a, b -> a to b }
}

/** Set all the elements in this array to the given element. */
fun <T> Array<T>.setAllTo(element: T) {
    indices.forEach { this[it] = element }
}

/** Convert this array to a [DefaultedList] */
fun Array<ItemStack>.toDefaultedList(): DefaultedList<ItemStack> {
    return DefaultedList.copyOf(ItemStack.EMPTY, *this)
}

fun <T, L : List<T>> Array<T>.setToList(list: L) {
    indices.forEach { this[it] = list[it] }
}

/** Checks if both arrays are the same size.
 * @throws IllegalStateException if the two arrays are not equal size */
fun <A, B> checkForSameSize(a: Array<A>, b: Array<B>) {
    if (a.size != b.size) throw IllegalStateException("Both arrays must be the same size.")
}

/** Utility function to allow destructuring 6 element lists. */
operator fun <T> List<T>.component6() = this[5]