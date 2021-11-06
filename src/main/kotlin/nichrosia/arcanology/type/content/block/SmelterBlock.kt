package nichrosia.arcanology.type.content.block

import nichrosia.arcanology.Arcanology.arcanology
import nichrosia.arcanology.type.content.block.entity.SmelterBlockEntity
import nichrosia.arcanology.type.content.block.settings.ArcanologyBlockSettings
import nichrosia.arcanology.type.content.gui.description.SmelterGuiDescription
import nichrosia.arcanology.type.energy.EnergyTier
import nichrosia.common.record.registrar.Registrar

@Suppress("LeakingThis")
open class SmelterBlock(
    settings: ArcanologyBlockSettings,
    tier: EnergyTier
) : FluidMachineBlock<SmelterBlock, SmelterGuiDescription, SmelterBlockEntity>(
    settings,
    ::SmelterBlockEntity,
    { Registrar.arcanology.blockEntity.smelter },
    tier
)