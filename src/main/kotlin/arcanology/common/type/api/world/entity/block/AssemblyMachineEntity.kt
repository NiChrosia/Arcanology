package arcanology.common.type.api.world.entity.block

import assemble.common.Assemble
import assemble.common.type.api.assembly.GradualAssembly
import assemble.common.type.api.assembly.type.gradual.GradualAssemblyType
import assemble.common.type.api.storage.Progressable
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class AssemblyMachineEntity<E : AssemblyMachineEntity<E, A, T>, A : GradualAssembly<E>, T : GradualAssemblyType<E, A>>(
    type: BlockEntityType<E>,
    val assemblyType: T,
    pos: BlockPos,
    state: BlockState
) : MachineBlockEntity<E>(type, pos, state), Progressable {
    override var progress = 0L

    open val assembly = Assemble.matching(assemblyType).first()

    override fun E.tick(world: World, pos: BlockPos, state: BlockState) {
        if (assembly.matches(this)) {
            assembly.tick(this)
        }
    }

    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)

        nbt.putLong("Progress", progress)
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)

        progress = nbt.getLong("Progress")
    }
}