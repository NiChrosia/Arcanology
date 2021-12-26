package arcanology.common.type.api.machine.component

import arcanology.common.type.impl.world.block.entity.MachineBlockEntity
import assemble.common.type.api.assembly.GradualAssembly
import io.github.cottonmc.cotton.gui.widget.WWidget

open class ModuleComponent<A : GradualAssembly<MachineBlockEntity>, W : WWidget>(val machine: MachineBlockEntity, val assembly: A, val widget: W)