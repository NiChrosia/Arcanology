package arcanology.common.type.api.world.entity.block

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

interface TickableBlockEntity {
    fun tick(world: World, pos: BlockPos, state: BlockState)
}