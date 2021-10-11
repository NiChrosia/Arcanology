package nichrosia.arcanology.type.content.block

import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.type.block.MachineBlock
import nichrosia.arcanology.type.content.block.entity.SeparatorBlockEntity
import nichrosia.arcanology.type.content.gui.description.SeparatorGUIDescription
import nichrosia.arcanology.type.energy.EnergyTier

@Suppress("LeakingThis")
open class SeparatorBlock(
    settings: Settings,
    tier: EnergyTier
) : MachineBlock<SeparatorBlock, SeparatorGUIDescription, SeparatorBlockEntity>(
    settings,
    ::SeparatorBlockEntity,
    { Registrar.blockEntity.separator },
    tier
)