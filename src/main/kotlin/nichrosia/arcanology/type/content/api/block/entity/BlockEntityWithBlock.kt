package nichrosia.arcanology.type.content.api.block.entity

import net.minecraft.block.Block

interface BlockEntityWithBlock<T : Block> {
    val block: T
}