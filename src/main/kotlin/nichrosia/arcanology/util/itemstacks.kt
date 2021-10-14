package nichrosia.arcanology.util

import net.minecraft.item.ItemStack
import kotlin.reflect.KMutableProperty0

/** Increments the count, and if it reaches the max, clamps it. */
fun ItemStack.increment(): ItemStack {
    return apply { count = clamp(count + 1, max = maxCount) }
}

/** Decrements the count, and if it's one, set to empty
 * @return A new [ItemStack] with the changes */
fun ItemStack.decrement(zeroStack: ItemStack = ItemStack.EMPTY): ItemStack {
    return if (count > 1) apply { count = clamp(count - 1) } else zeroStack
}

/** Merge the count of this [ItemStack] and the other, while keeping the item. */
fun ItemStack.mergeCount(other: ItemStack): ItemStack {
    return ItemStack(item, count + other.count)
}

/** Utility function to easily decrement an [ItemStack] field. */
fun KMutableProperty0<ItemStack>.decrement() {
    set(get().decrement())
}

