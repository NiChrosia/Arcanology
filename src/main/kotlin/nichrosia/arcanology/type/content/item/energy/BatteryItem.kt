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
 * For example: a solid-state battery is far more effective than a basic nickel-zinc battery.
 *
 * Batteries:
 *
 * Nickel-zinc battery ([EnergyTier.T1]): comprised mainly of nickel and zinc, which offers a minor power capacity
 * (`5K EF`, Electric Flux)
 *
 * Lithium-ion battery ([EnergyTier.T2]): composed of lithium and cobalt, which offers a medium power capacity
 * (`25K EF`)
 *
 * Lithium-sulfur battery ([EnergyTier.T3]): made up of lithium and sulfuric acid, which offers a large power
 * capacity  (`100K EF`)
 *
 * Solid-state battery ([EnergyTier.T4]): made up of lithium, sulfuric acid, and a thermosetting plastic, such as
 * fiberglass, which offers a very large power capacity (`750K EF`)
 *
 * Supercapacitor battery ([EnergyTier.T5]): A heavily compacted mass of supercapacitors made up of lead, sulfuric acid,
 * and carbon, which offers a massive power capacity and (dis)charging speed. (`10M EF`) */
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