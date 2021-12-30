package arcanology.common.type.impl.machine.module

import arcanology.common.Arcanology
import arcanology.common.type.api.machine.module.MachineModule
import arcanology.common.type.api.machine.module.ModuleType
import arcanology.common.type.impl.assembly.gradual.BlankGradualAssembly
import arcanology.common.type.impl.world.block.entity.MachineBlockEntity

open class BlankModuleType : ModuleType<BlankGradualAssembly<MachineBlockEntity>>({
    val assembly = BlankGradualAssembly<MachineBlockEntity>(Arcanology.identify("blank"))

    MachineModule(it, assembly, listOf())
}, {})