package arcanology.util.world.items

import net.minecraft.item.ItemStack
import arcanology.util.primitives.numbers.clamp

/** Increments the count, and if it reaches the max, clamps it. */
fun ItemStack.increment(): ItemStack {
    return apply { count = clamp(count + 1, max = maxCount) }
}

/** Decrements the count, and if it's one, set to empty
 * @return A new [ItemStack] with the changes */
fun ItemStack.decrement(zeroStack: ItemStack = ItemStack.EMPTY): ItemStack {
    return if (count > 1) apply { count = clamp(count - 1) } else zeroStack
}