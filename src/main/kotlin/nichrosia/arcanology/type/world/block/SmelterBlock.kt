package nichrosia.arcanology.type.world.block

import nichrosia.arcanology.type.world.block.entity.SmelterBlockEntity
import nichrosia.arcanology.type.world.block.settings.MachineBlockSettings
import nichrosia.arcanology.type.graphics.ui.gui.description.SmelterGuiDescription

@Suppress("LeakingThis")
open class SmelterBlock(
    settings: MachineBlockSettings
) : FluidMachineBlock<SmelterBlock, SmelterGuiDescription, SmelterBlockEntity>(
    settings,
    ::SmelterBlockEntity
)