package nichrosia.arcanology.type.item.energy

import net.minecraft.item.ItemStack
import nichrosia.arcanology.type.tooltip.energy.EnergyTooltipDataProvider
import nichrosia.arcanology.util.energyIO
import kotlin.math.roundToInt

/** @author GabrielOlvH, with minor modifications by NiChrosia */
interface EnergyItem : EnergyTooltipDataProvider {
    fun getDurabilityBarProgress(stack: ItemStack): Int {
        val energyIo = stack.energyIO ?: return 0
        return (13.0f - (energyIo.energyCapacity - energyIo.energy) * 13.0f / energyIo.energyCapacity)
            .roundToInt() / stack.count
    }

    fun hasDurabilityBar(stack: ItemStack): Boolean = (stack.energyIO?.energy ?: 0.0) > 0

    fun getDurabilityBarColor(stack: ItemStack): Int {
        val durability = getDurabilityBarProgress(stack) / 13f
        val r = (250 - (16 * durability)).toInt() and 255 shl 16
        val g = 255 shl 8
        val b = 199 - (199 * durability).toInt()
        return r or g or b
    }
}