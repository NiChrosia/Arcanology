package arcanology.common.type.api.world.entity.block

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos

abstract class MachineBlockEntity<E : MachineBlockEntity<E>>(
    type: BlockEntityType<E>,
    pos: BlockPos,
    state: BlockState
) : BlockEntity(type, pos, state), TickableBlockEntity<E>