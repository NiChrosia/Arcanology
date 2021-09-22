package nichrosia.arcanology.type.rune.impl

import net.minecraft.item.ItemStack
import nichrosia.arcanology.type.instance.rune.Rune.Companion.mana
import nichrosia.arcanology.type.rune.RuneType
import kotlin.math.roundToInt

open class ManaboundRuneType(name: String = "manabound") : RuneType(name) {
    override fun useDurability(itemStack: ItemStack): Boolean {
        return itemStack.mana <= 0
    }

    override fun getItemBarStep(stack: ItemStack): Int {
        return 13 - (stack.mana.toDouble() / maxMana.toDouble() * 13).roundToInt()
    }

    override fun isItemBarVisible(stack: ItemStack): Boolean {
        return stack.mana > 0
    }
}