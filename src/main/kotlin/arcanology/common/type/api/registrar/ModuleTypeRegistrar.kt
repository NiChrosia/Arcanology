package arcanology.common.type.api.registrar

import arcanology.common.type.api.machine.module.ModuleType
import arcanology.common.type.impl.world.block.entity.MachineBlockEntity
import assemble.common.type.api.assembly.GradualAssembly
import net.minecraft.util.Identifier
import nucleus.common.builtin.division.ModRoot
import nucleus.common.registrar.type.BinaryRegistrar

open class ModuleTypeRegistrar<R : ModRoot<R>>(root: R) : BinaryRegistrar<Identifier, ModuleType<out GradualAssembly<MachineBlockEntity>>, R>(root)