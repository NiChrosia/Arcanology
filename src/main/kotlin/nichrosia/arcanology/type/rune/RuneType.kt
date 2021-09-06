package nichrosia.arcanology.type.rune

import net.minecraft.item.ItemStack
import net.minecraft.util.math.MathHelper
import nichrosia.arcanology.type.element.Element
import kotlin.properties.Delegates

@Suppress("LeakingThis")
open class RuneType(open val name: String) {
    open val maxMana = 0
    open val minLevel = 1
    open val maxLevel = 1
    open val overrideItemBar = false
    open val miningSpeedMultiplier = 1f
    open val element = Element.Mana

    open val item: ItemStack = ItemStack.EMPTY
    open var translationKey = "rune.arcanology.$name"

    open var id by Delegates.notNull<Int>()

    init {
        types += this

        id = types.size - 1
    }

    open fun isItemBarVisible(stack: ItemStack) = false
    open fun getItemBarStep(stack: ItemStack) = 13

    open fun getItemBarColor(stack: ItemStack): Int {
        val f = 0.0f.coerceAtLeast((stack.item.maxDamage.toFloat() - stack.damage.toFloat()) / stack.item.maxDamage.toFloat())

        return MathHelper.hsvToRgb(f / 3.0f, 1.0f, 1.0f)
    }

    open fun useDurability(itemStack: ItemStack): Boolean {
        return true
    }

    companion object {
        val types = mutableListOf<RuneType>()
    }
}