package nichrosia.arcanology.type.block.entity

import net.minecraft.block.Block

/** An interface to represent a block reference for a block entity. */
interface BlockEntityWithBlock<T : Block> {
    val block: T
}