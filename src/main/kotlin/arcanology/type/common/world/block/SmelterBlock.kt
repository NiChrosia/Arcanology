package arcanology.type.common.world.block

import arcanology.type.common.ui.descriptor.SmelterGuiDescriptor
import arcanology.type.common.world.block.entity.SmelterBlockEntity
import arcanology.type.common.world.block.settings.FluidMachineBlockSettings

@Suppress("LeakingThis")
open class SmelterBlock(
    settings: FluidMachineBlockSettings
) : FluidMachineBlock<SmelterBlock, SmelterGuiDescriptor, SmelterBlockEntity>(
    settings,
    ::SmelterBlockEntity
)