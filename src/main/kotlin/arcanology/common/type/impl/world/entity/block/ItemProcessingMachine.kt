package arcanology.common.type.impl.world.entity.block

import arcanology.common.Arcanology
import arcanology.common.type.api.world.entity.block.AssemblyMachineEntity
import arcanology.common.type.impl.assembly.gradual.energy.EnergyItemProcessingAssembly
import arcanology.common.type.impl.assembly.type.gradual.energy.EnergyItemProcessingType
import assemble.common.type.api.storage.EnergyInventory
import assemble.common.type.api.storage.ItemInventory
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleSlotStorage
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import team.reborn.energy.api.base.SimpleEnergyStorage

open class ItemProcessingMachine(
    pos: BlockPos,
    state: BlockState
) : AssemblyMachineEntity<ItemProcessingMachine, EnergyItemProcessingAssembly<ItemProcessingMachine>, EnergyItemProcessingType<ItemProcessingMachine>>(
    Arcanology.content.blockEntity.itemProcessingMachine,
    pos,
    state
), EnergyInventory, ItemInventory {
    override val assemblyType = Arcanology.content.assemblyType.itemProcessing

    override val energyStorage = SimpleEnergyStorage(0L, 0L, 0L)
    override val itemStorage = CombinedStorage<ItemVariant, SingleSlotStorage<ItemVariant>>(mutableListOf())
}