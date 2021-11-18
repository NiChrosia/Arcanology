package arcanology.type.api.common.world.block

import net.minecraft.block.Block
import arcanology.type.common.world.data.tag.ContentTag

interface TaggedBlock {
    val tags: List<ContentTag<Block>>
}