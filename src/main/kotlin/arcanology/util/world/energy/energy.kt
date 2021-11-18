@file:Suppress("UnstableApiUsage")

package arcanology.util.world.energy

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext
import net.minecraft.item.ItemStack
import team.reborn.energy.api.EnergyStorage

/** Get the [EnergyStorage] for this [ItemStack] in the energy API. */
fun ItemStack.getEnergyStorage(context: ContainerItemContext = ContainerItemContext.withInitial(this)): EnergyStorage? {
    return EnergyStorage.ITEM.find(this, context)
}