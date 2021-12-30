package arcanology.common.type.api.world.entity.block

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

interface TickableBlockEntity<T> where T : BlockEntity, T : TickableBlockEntity<T> {
    fun T.tick(world: World, pos: BlockPos, state: BlockState)
}