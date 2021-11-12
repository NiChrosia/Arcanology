package nichrosia.arcanology.util

import net.minecraft.block.BlockState
import net.minecraft.state.property.Property
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

fun <T : Comparable<T>> setState(world: World, pos: BlockPos, currentState: BlockState, property: Property<T>, value: T): BlockState {
    return currentState.with(property, value).also {
        world.setBlockState(pos, it)
    }
}