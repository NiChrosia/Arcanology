package nichrosia.arcanology.type.rune

import net.minecraft.item.ItemStack
import nichrosia.arcanology.content.AMaterials
import nichrosia.arcanology.content.ARunes
import nichrosia.arcanology.type.rune.base.Rune.Companion.mana
import nichrosia.arcanology.type.rune.base.RuneType
import kotlin.math.roundToInt

open class ManaboundRuneType : RuneType("manabound") {
    override val item: ItemStack
        get() = ARunes.runes["manabound"] ?: ItemStack.EMPTY

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