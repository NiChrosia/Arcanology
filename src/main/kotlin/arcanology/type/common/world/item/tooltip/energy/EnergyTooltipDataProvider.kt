@file:Suppress("DEPRECATION", "UnstableApiUsage")

package arcanology.type.common.world.item.tooltip.energy

import net.minecraft.item.ItemStack
import arcanology.type.common.world.item.tooltip.CustomTooltipData
import arcanology.type.common.world.item.tooltip.CustomTooltipDataProvider
import arcanology.util.world.energy.getEnergyStorage

/** @author GabrielOlvH */
interface EnergyTooltipDataProvider : CustomTooltipDataProvider {
    override fun getData(stack: ItemStack): List<CustomTooltipData> {
        val handler = stack.getEnergyStorage() ?: return emptyList()
        return listOf(EnergyTooltipData(handler.amount, handler.capacity))
    }
}