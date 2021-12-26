package arcanology.common.type.api.machine.module

import arcanology.common.type.api.machine.component.ModuleComponent
import arcanology.common.type.impl.world.block.entity.MachineBlockEntity
import assemble.common.type.api.assembly.GradualAssembly
import io.github.cottonmc.cotton.gui.widget.WWidget

open class MachineModule<A : GradualAssembly<MachineBlockEntity>>(
    val machine: MachineBlockEntity,
    val assembly: A,
    val components: List<ModuleComponent<A, out WWidget>>
)