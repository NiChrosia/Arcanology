package arcanology.common.type.api.world.entity.block

import assemble.common.Assemble
import assemble.common.type.api.assembly.GradualAssembly
import assemble.common.type.api.assembly.type.AssemblyType
import assemble.common.type.api.storage.ProgressInventory
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class AssemblyMachineEntity<A : GradualAssembly<AssemblyMachineEntity<A, T>>, T : AssemblyType<AssemblyMachineEntity<A, T>, A>>(
    type: BlockEntityType<out AssemblyMachineEntity<A, T>>,
    pos: BlockPos,
    state: BlockState
) : MachineBlockEntity(type, pos, state), ProgressInventory {
    abstract val assemblyType: T

    override var progress = 0L

    override fun tick(world: World, pos: BlockPos, state: BlockState) {
        val assembly = Assemble.matching(assemblyType).first()

        if (assembly.matches(this)) {
            assembly.tick(this)
        }
    }
}