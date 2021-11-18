package nichrosia.arcanology.type.world.item.energy

import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import nichrosia.arcanology.type.world.data.energy.EnergyTier
import team.reborn.energy.api.base.SimpleBatteryItem

/** The base class for all batteries.
 *
 * The quality, efficiency, speed, and storage capacity depends on the architecture of the battery.
 * For example: a solid-state battery is far more effective than a basic nickel-zinc battery. */
@Suppress("MemberVisibilityCanBePrivate", "LeakingThis")
open class BatteryItem(settings: Settings, val tier: EnergyTier) : Item(settings), EnergyItem, SimpleBatteryItem {
    override fun getItemBarColor(stack: ItemStack): Int = getDurabilityBarColor(stack)
    override fun isItemBarVisible(stack: ItemStack): Boolean = hasDurabilityBar(stack)
    override fun getItemBarStep(stack: ItemStack): Int = getDurabilityBarProgress(stack)
    override fun isEnchantable(stack: ItemStack): Boolean = false
    override fun canRepair(stack: ItemStack, ingredient: ItemStack): Boolean = false
    override fun getEnergyCapacity() = tier.storage
    override fun getEnergyMaxInput() = tier.maxInputSpeed
    override fun getEnergyMaxOutput() = tier.maxOutputSpeed
}