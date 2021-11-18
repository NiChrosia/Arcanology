package nichrosia.arcanology.type.api.world.block.entity

import net.minecraft.block.Block

interface BlockEntityWithBlock<T : Block> {
    val block: T
}