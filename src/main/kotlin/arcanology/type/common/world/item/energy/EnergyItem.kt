@file:Suppress("UnstableApiUsage", "DEPRECATION")

package arcanology.type.common.world.item.energy

import net.minecraft.item.ItemStack
import arcanology.type.common.world.item.tooltip.energy.EnergyTooltipDataProvider
import arcanology.util.world.energy.getEnergyStorage

/** @author GabrielOlvH, with minor modifications by NiChrosia */
interface EnergyItem : EnergyTooltipDataProvider {
    fun getDurabilityBarProgress(stack: ItemStack): Int {
        val energyIo = stack.getEnergyStorage() ?: return 0
        return ((13 - (energyIo.capacity - energyIo.amount) * 13 / energyIo.capacity) / stack.count).toInt()
    }

    fun hasDurabilityBar(stack: ItemStack): Boolean = (stack.getEnergyStorage()?.amount ?: 0L) > 0

    fun getDurabilityBarColor(stack: ItemStack): Int {
        val durability = getDurabilityBarProgress(stack) / 13f
        val r = (250 - (16 * durability)).toInt() and 255 shl 16
        val g = 255 shl 8
        val b = 199 - (199 * durability).toInt()
        return r or g or b
    }
}