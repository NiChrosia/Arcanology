package arcanology.type.common.registar.tag

import net.minecraft.item.Item
import arcanology.type.common.world.data.tag.ContentTag
import arcanology.type.common.world.data.tag.ItemTag
import nichrosia.common.identity.ID
import nichrosia.common.record.member.MemberList
import nichrosia.common.record.registry.content.ContentRegistry

interface ItemTagRegistrar : TagRegistrar<Item> {
    override fun emptyTagOf(location: ID): ContentTag<Item> {
        return ItemTag.Basic(location)
    }

    open class Basic : ItemTagRegistrar {
        override val registry: ContentRegistry<ContentTag<Item>> = ContentRegistry.Basic()
        override val members: MemberList<ContentTag<Item>> = MemberList.Basic()
    }
}