package nichrosia.arcanology.type.content.block

import nichrosia.arcanology.registry.category.ArcanologyCategory.arcanology
import nichrosia.arcanology.type.content.block.entity.SeparatorBlockEntity
import nichrosia.arcanology.type.content.gui.description.SeparatorGuiDescription
import nichrosia.arcanology.type.energy.EnergyTier
import nichrosia.registry.Registrar

@Suppress("LeakingThis")
open class SeparatorBlock(
    settings: Settings,
    tier: EnergyTier
) : MachineBlock<SeparatorBlock, SeparatorGuiDescription, SeparatorBlockEntity>(
    settings,
    ::SeparatorBlockEntity,
    { Registrar.arcanology.blockEntity.separator },
    tier
)