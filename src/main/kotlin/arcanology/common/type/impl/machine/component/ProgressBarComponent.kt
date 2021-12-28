package arcanology.common.type.impl.machine.component

import arcanology.common.type.api.machine.component.ModuleComponent
import arcanology.common.type.impl.gui.widget.WProcessingBar
import arcanology.common.type.impl.world.block.entity.MachineBlockEntity
import assemble.common.type.api.assembly.GradualAssembly

open class ProgressBarComponent<A : GradualAssembly<MachineBlockEntity>>(
    machine: MachineBlockEntity,
    assembly: A,
) : ModuleComponent<A, WProcessingBar>(
    machine,
    assembly,
    WProcessingBar(machine::progress, assembly::end)
)