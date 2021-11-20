package arcanology.type.common.world.block.machine.progressor

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

/** An interface to provide easier progression of block entity goals without variable spam. */
interface Progressor {
    val maxProgress: Long
    var incrementAmount: Long
    var progress: Long

    fun valid(world: World, pos: BlockPos, state: BlockState): Boolean {
        return !world.isClient
    }

    fun consume(world: World, pos: BlockPos, state: BlockState) {
        if (world.isClient) return

        progress += incrementAmount
    }

    fun idle(world: World, pos: BlockPos, state: BlockState) {
        if (world.isClient) return
    }

    fun progress(world: World, pos: BlockPos, state: BlockState) {
        if (world.isClient) return

        if (valid(world, pos, state)) {
            consume(world, pos, state)

            if (progress >= maxProgress) {
                complete(world, pos, state)
            }
        } else {
            idle(world, pos, state)
        }
    }

    fun complete(world: World, pos: BlockPos, state: BlockState) {
        if (world.isClient) return

        progress = 0L
    }
}