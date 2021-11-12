package nichrosia.arcanology.type.content.block

import nichrosia.arcanology.type.content.block.entity.SmelterBlockEntity
import nichrosia.arcanology.type.content.block.settings.MachineBlockSettings
import nichrosia.arcanology.type.content.gui.description.SmelterGuiDescription

@Suppress("LeakingThis")
open class SmelterBlock(
    settings: MachineBlockSettings
) : FluidMachineBlock<SmelterBlock, SmelterGuiDescription, SmelterBlockEntity>(
    settings,
    ::SmelterBlockEntity
)