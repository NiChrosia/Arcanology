package nichrosia.arcanology.type.tooltip

import net.minecraft.item.ItemStack

interface CustomTooltipDataProvider {
    fun getData(stack: ItemStack): List<CustomTooltipData>
}