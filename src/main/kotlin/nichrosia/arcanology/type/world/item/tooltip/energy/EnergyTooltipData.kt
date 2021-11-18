package nichrosia.arcanology.type.world.item.tooltip.energy

import net.minecraft.client.gui.tooltip.TooltipComponent
import nichrosia.arcanology.type.world.item.tooltip.CustomTooltipData

/** @author GabrielOlvH */
data class EnergyTooltipData(val energy: Long, val maxEnergy: Long) : CustomTooltipData {
    override fun toComponent(): TooltipComponent = EnergyTooltipComponent(this)
}