package nichrosia.arcanology.util

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.state.property.Property
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import nichrosia.arcanology.type.nbt.NbtContainer

fun <T : Comparable<T>> setState(world: World, pos: BlockPos, currentState: BlockState, property: Property<T>, value: T): BlockState {
    return currentState.with(property, value).also {
        world.setBlockState(pos, it)
    }
}

fun <E> E.writeNbtObjects(nbt: NbtCompound): NbtCompound where E : BlockEntity, E : NbtContainer {
    return nbt.apply {
        nbtObjects.forEach { it.writeNbt(this) }
    }
}

fun <E> E.readNbtObjects(nbt: NbtCompound): NbtCompound where E : BlockEntity, E : NbtContainer {
    return nbt.apply {
        nbtObjects.forEach { it.readNbt(nbt) }
    }
}