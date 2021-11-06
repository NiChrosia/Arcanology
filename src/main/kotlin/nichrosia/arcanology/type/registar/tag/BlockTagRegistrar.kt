package nichrosia.arcanology.type.registar.tag

import net.minecraft.block.Block
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.data.runtimeresource.tag.BlockTag
import nichrosia.arcanology.type.data.runtimeresource.tag.ContentTag
import nichrosia.common.identity.ID
import nichrosia.common.record.member.MemberList
import nichrosia.common.record.registry.content.ContentRegistry

interface BlockTagRegistrar : TagRegistrar<Block> {
    override fun emptyTagOf(location: ID): ContentTag<Block> {
        return BlockTag.Basic(location)
    }

    override fun publish() {
        registry.catalog.values.forEach(Arcanology.packManager.tags::add)
    }

    open class Basic : BlockTagRegistrar {
        override val registry: ContentRegistry<ContentTag<Block>> = ContentRegistry.Basic()
        override val members: MemberList<ContentTag<Block>> = MemberList.Basic()
    }
}