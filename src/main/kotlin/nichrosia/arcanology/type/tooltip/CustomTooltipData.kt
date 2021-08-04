package nichrosia.arcanology.type.tooltip

import net.minecraft.client.gui.tooltip.TooltipComponent
import net.minecraft.client.item.TooltipData

/** @author GabrielOlvH */
interface CustomTooltipData : TooltipData {
    fun toComponent(): TooltipComponent
}