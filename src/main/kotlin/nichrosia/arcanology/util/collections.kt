package nichrosia.arcanology.util

import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList

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

/** Utility function to allow destructuring 6 element lists. */
operator fun <T> List<T>.component6() = this[5]