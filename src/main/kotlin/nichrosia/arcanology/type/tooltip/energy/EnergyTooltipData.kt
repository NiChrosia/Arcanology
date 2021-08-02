package nichrosia.arcanology.type.tooltip.energy

import net.minecraft.client.gui.tooltip.TooltipComponent
import nichrosia.arcanology.type.tooltip.CustomTooltipData

data class EnergyTooltipData(val energy: Double, val maxEnergy: Double) : CustomTooltipData {
    override fun toComponent(): TooltipComponent = EnergyTooltipComponent(this)
}