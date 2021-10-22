package nichrosia.arcanology.type.content.item.energy

import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import nichrosia.arcanology.type.energy.EnergyTier
import nichrosia.arcanology.type.id.item.IdentifiedItem
import nichrosia.arcanology.type.item.energy.EnergyItem
import team.reborn.energy.api.base.SimpleBatteryItem

/** The base class for all batteries.
 *
 * The quality, efficiency, speed, and storage capacity depends on the architecture of the battery.
 * For example: a solid-state battery is far more effective than a basic nickel-zinc battery. */
@Suppress("MemberVisibilityCanBePrivate", "LeakingThis")
open class BatteryItem(settings: Settings, ID: Identifier, val tier: EnergyTier) : IdentifiedItem(settings, ID), EnergyItem, SimpleBatteryItem {
    override fun getItemBarColor(stack: ItemStack): Int = getDurabilityBarColor(stack)
    override fun isItemBarVisible(stack: ItemStack): Boolean = hasDurabilityBar(stack)
    override fun getItemBarStep(stack: ItemStack): Int = getDurabilityBarProgress(stack)
    override fun isEnchantable(stack: ItemStack): Boolean = false
    override fun canRepair(stack: ItemStack, ingredient: ItemStack): Boolean = false
    override fun getEnergyCapacity() = tier.storage
    override fun getEnergyMaxInput() = tier.maxInputPerTick
    override fun getEnergyMaxOutput() = tier.maxOutputPerTick
}