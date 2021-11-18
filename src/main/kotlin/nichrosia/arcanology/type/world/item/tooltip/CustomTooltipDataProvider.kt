package nichrosia.arcanology.type.world.item.tooltip

import net.minecraft.item.ItemStack

/** @author GabrielOlvH */
interface CustomTooltipDataProvider {
    fun getData(stack: ItemStack): List<CustomTooltipData>
}