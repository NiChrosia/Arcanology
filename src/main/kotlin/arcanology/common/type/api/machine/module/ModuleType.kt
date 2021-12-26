package arcanology.common.type.api.machine.module

import arcanology.common.type.impl.world.block.entity.MachineBlockEntity
import assemble.common.type.api.assembly.GradualAssembly

open class ModuleType<A : GradualAssembly<MachineBlockEntity>>(val provider: (MachineBlockEntity) -> MachineModule<A>)