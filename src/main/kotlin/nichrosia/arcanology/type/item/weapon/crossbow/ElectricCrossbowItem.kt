package nichrosia.arcanology.type.item.weapon.crossbow

import dev.technici4n.fasttransferlib.api.Simulation
import dev.technici4n.fasttransferlib.api.energy.EnergyApi
import dev.technici4n.fasttransferlib.api.energy.base.SimpleItemEnergyIo
import net.minecraft.item.ItemStack
import nichrosia.arcanology.energy.EnergyTier
import nichrosia.arcanology.type.item.energy.EnergyItem
import nichrosia.arcanology.util.energyIO

@Suppress("MemberVisibilityCanBePrivate", "LeakingThis")
open class ElectricCrossbowItem(settings: Settings, val energyUsage: Double, tier: EnergyTier) : OpenCrossbowItem(settings), EnergyItem {
    init {
        EnergyApi.ITEM.registerForItems(
            SimpleItemEnergyIo.getProvider(
            tier.storage,
            tier.maxInput,
            tier.maxOutput
        ), this)
    }

    override fun getItemBarColor(stack: ItemStack): Int = getDurabilityBarColor(stack)
    override fun isItemBarVisible(stack: ItemStack): Boolean = hasDurabilityBar(stack)
    override fun getItemBarStep(stack: ItemStack): Int = getDurabilityBarProgress(stack)
    override fun isEnchantable(stack: ItemStack): Boolean = false

    open fun ItemStack.extractEnergy(amount: Double) {
        if (energyIO?.supportsExtraction() == true) energyIO?.extract(amount, Simulation.ACT)
    }

    override fun consume(itemStack: ItemStack) {
        itemStack.extractEnergy(energyUsage)
    }

    override fun consumeValid(itemStack: ItemStack): Boolean {
        return itemStack.energyIO?.energy?.let { it > 0.1 } ?: false
    }

    override fun getConsumePullTimeSubtractor(itemStack: ItemStack): Int {
        return 10
    }

    override fun getConsumeSpeedMultiplier(itemStack: ItemStack): Float {
        return 3f
    }
}