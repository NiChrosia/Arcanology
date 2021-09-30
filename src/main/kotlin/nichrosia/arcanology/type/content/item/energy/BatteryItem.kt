package nichrosia.arcanology.type.content.item.energy

import dev.technici4n.fasttransferlib.api.energy.EnergyApi
import dev.technici4n.fasttransferlib.api.energy.base.SimpleItemEnergyIo
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import nichrosia.arcanology.type.energy.EnergyTier
import nichrosia.arcanology.type.item.energy.EnergyItem

/** The base class for all batteries.
 *
 * The quality, efficiency, speed, and storage capacity depends on the architecture of the battery.
 * For example: a solid-state battery is far more effective than a basic nickel-zinc battery. */
@Suppress("MemberVisibilityCanBePrivate", "LeakingThis")
open class BatteryItem(settings: Settings, tier: EnergyTier) : Item(settings), EnergyItem {
    init {
        EnergyApi.ITEM.registerForItems(SimpleItemEnergyIo.getProvider(
            tier.storage, 
            tier.maxInput, 
            tier.maxOutput
        ), this)
    }

    override fun getItemBarColor(stack: ItemStack): Int = getDurabilityBarColor(stack)
    override fun isItemBarVisible(stack: ItemStack): Boolean = hasDurabilityBar(stack)
    override fun getItemBarStep(stack: ItemStack): Int = getDurabilityBarProgress(stack)
    override fun isEnchantable(stack: ItemStack): Boolean = false
    override fun canRepair(stack: ItemStack, ingredient: ItemStack): Boolean = false
}