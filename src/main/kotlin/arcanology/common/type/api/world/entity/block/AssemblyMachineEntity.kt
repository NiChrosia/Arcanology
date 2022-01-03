package arcanology.common.type.api.world.entity.block

import assemble.common.Assemble
import assemble.common.type.api.assembly.GradualAssembly
import assemble.common.type.api.assembly.type.AssemblyType
import assemble.common.type.api.storage.Progressable
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class AssemblyMachineEntity<E : AssemblyMachineEntity<E, A, T>, A : GradualAssembly<E>, T : AssemblyType<E, A>>(
    type: BlockEntityType<E>,
    pos: BlockPos,
    state: BlockState
) : MachineBlockEntity<E>(type, pos, state), Progressable {
    abstract val assemblyType: T

    override var progress = 0L

    override fun E.tick(world: World, pos: BlockPos, state: BlockState) {
        val assembly = Assemble.matching(assemblyType).first()

        if (assembly.matches(this)) {
            assembly.tick(this)
        }
    }
}