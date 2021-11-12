package nichrosia.arcanology.type.content.api.block

import net.minecraft.block.Block
import nichrosia.arcanology.type.data.runtimeresource.tag.ContentTag

interface TaggedBlock {
    val tags: List<ContentTag<Block>>
}