package nichrosia.arcanology.type.content.block

import net.minecraft.block.Block
import nichrosia.arcanology.type.data.runtimeresource.tag.ContentTag

interface TaggedBlock {
    val tags: List<ContentTag<Block>>
}