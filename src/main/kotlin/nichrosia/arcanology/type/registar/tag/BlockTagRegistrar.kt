package nichrosia.arcanology.type.registar.tag

import net.minecraft.block.Block
import nichrosia.arcanology.type.world.data.tag.BlockTag
import nichrosia.arcanology.type.world.data.tag.ContentTag
import nichrosia.common.identity.ID
import nichrosia.common.record.member.MemberList
import nichrosia.common.record.registry.content.ContentRegistry

interface BlockTagRegistrar : TagRegistrar<Block> {
    override fun emptyTagOf(location: ID): ContentTag<Block> {
        return BlockTag.Basic(location)
    }

    open class Basic : BlockTagRegistrar {
        override val registry: ContentRegistry<ContentTag<Block>> = ContentRegistry.Basic()
        override val members: MemberList<ContentTag<Block>> = MemberList.Basic()
    }
}