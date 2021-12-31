package arcanology.common.type.impl.world.entity.block

import arcanology.common.Arcanology
import arcanology.common.type.api.world.entity.block.AssemblyMachineEntity
import arcanology.common.type.api.world.entity.block.ScreenBlockEntity
import arcanology.common.type.impl.assembly.gradual.energy.EnergyItemProcessingAssembly
import arcanology.common.type.impl.assembly.type.gradual.energy.EnergyItemProcessingType
import arcanology.common.type.impl.gui.description.ItemProcessingDescription
import assemble.common.type.api.storage.EnergyInventory
import assemble.common.type.api.storage.ItemInventory
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos

open class ItemProcessingMachine(
    pos: BlockPos,
    state: BlockState
) : AssemblyMachineEntity<ItemProcessingMachine, EnergyItemProcessingAssembly<ItemProcessingMachine>, EnergyItemProcessingType<ItemProcessingMachine>>(
    Arcanology.content.blockEntity.itemProcessingMachine,
    pos,
    state
), EnergyInventory, ItemInventory, ScreenBlockEntity<ItemProcessingMachine, ItemProcessingDescription> {
    override val assemblyType = Arcanology.content.assemblyType.itemProcessing
    override val handlerConstructor = ::ItemProcessingDescription

    override val energyStorage = Arcanology.content.energyTier.standard.fullStorageOf()
    override val itemStorage = itemStorageOf(2)

    override val title = blockName()
}