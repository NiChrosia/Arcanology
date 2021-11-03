package nichrosia.arcanology.util

import net.minecraft.item.ItemStack
import net.minecraft.util.collection.DefaultedList

fun <T> MutableList<T>.setAllTo(element: T) {
    indices.forEach { this[it] = element }
}

fun MutableList<ItemStack>.toDefaultedList(): DefaultedList<ItemStack> {
    return DefaultedList.copyOf(ItemStack.EMPTY, *toTypedArray())
}

fun <T, L : List<T>> MutableList<T>.setToList(list: L) {
    indices.forEach { this[it] = list[it] }
}

inline fun <reified T> T.repeat(times: Int, noinline modifier: (T) -> T = { it }) = Array(times) { modifier(this) }.toMutableList()

operator fun <T> List<T>.component6() = this[5]