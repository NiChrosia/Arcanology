package nichrosia.arcanology.type.item.energy

import dev.technici4n.fasttransferlib.api.energy.EnergyApi
import dev.technici4n.fasttransferlib.api.energy.base.SimpleItemEnergyIo
import net.minecraft.item.Item
import net.minecraft.item.ItemStack

/** The base class for all batteries.
 *
 * The quality, efficiency, speed, and storage capacity depends on the architecture of the battery.
 * For example: a solid-state battery is far more effective than a basic nickel-zinc battery.
 *
 * Batteries:
 *
 * Nickel-zinc battery: comprised mainly of nickel and zinc, which offers a minor power capacity (`1K EF`, Electricite Flux)
 *
 * Lithium-ion battery: composed of lithium and cobalt, which offers a medium power capacity (`10K EF`)
 *
 * Lithium-sulfur battery: made up of lithium and sulfuric acid, which offers a large power capacity  (`50K EF`)
 *
 * Solid-state battery: made up of lithium, sulfuric acid, and a thermosetting plastic, such as fiberglass, which
 * offers a very large power capacity (`200K EF`)
 *
 * Supercapacitor battery: comprised of supercapacitors made up of lead, sulfuric acid, and carbon, which offers a
 * massive power capacity (`1M EF`) */
class BatteryItem(settings: Settings, maxStored: Double, maxInsertion: Double, maxExtraction: Double) : Item(settings), EnergyItem {
    init {
        EnergyApi.ITEM.registerForItems(SimpleItemEnergyIo.getProvider(maxStored, maxInsertion, maxExtraction), this)
    }

    override fun getItemBarColor(stack: ItemStack): Int = getDurabilityBarColor(stack)
    override fun isItemBarVisible(stack: ItemStack): Boolean = hasDurabilityBar(stack)
    override fun getItemBarStep(stack: ItemStack): Int = getDurabilityBarProgress(stack)
    override fun isEnchantable(stack: ItemStack): Boolean = false
    override fun canRepair(stack: ItemStack, ingredient: ItemStack): Boolean = false
}