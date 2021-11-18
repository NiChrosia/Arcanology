package nichrosia.arcanology.type.api.world.block

import net.minecraft.block.Block
import nichrosia.arcanology.type.world.data.tag.ContentTag

interface TaggedBlock {
    val tags: List<ContentTag<Block>>
}