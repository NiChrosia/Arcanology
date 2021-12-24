package arcanology.type.api.world.block.entity.progressor

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

open class Progressor(initial: Long, val increment: () -> Long, val max: Long) {
    open var progress = initial

    open fun valid(world: World, pos: BlockPos, state: BlockState): Boolean {
        return true
    }

    open fun idle(world: World, pos: BlockPos, state: BlockState) {

    }

    open fun progress(world: World, pos: BlockPos, state: BlockState) {
        if (valid(world, pos, state)) {
            progress += increment()

            if (progress >= max) {
                complete(world, pos, state)
            }
        } else {
            idle(world, pos, state)
        }
    }

    open fun complete(world: World, pos: BlockPos, state: BlockState) {
        progress = 0L
    }
}