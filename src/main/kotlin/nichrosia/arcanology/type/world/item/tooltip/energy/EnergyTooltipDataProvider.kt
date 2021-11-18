@file:Suppress("DEPRECATION", "UnstableApiUsage")

package nichrosia.arcanology.type.world.item.tooltip.energy

import net.minecraft.item.ItemStack
import nichrosia.arcanology.type.world.item.tooltip.CustomTooltipData
import nichrosia.arcanology.type.world.item.tooltip.CustomTooltipDataProvider
import nichrosia.arcanology.util.getEnergyStorage

/** @author GabrielOlvH */
interface EnergyTooltipDataProvider : CustomTooltipDataProvider {
    override fun getData(stack: ItemStack): List<CustomTooltipData> {
        val handler = stack.getEnergyStorage() ?: return emptyList()
        return listOf(EnergyTooltipData(handler.amount, handler.capacity))
    }
}