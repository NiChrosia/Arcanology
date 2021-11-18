package nichrosia.arcanology.type.world.data.tag

import net.minecraft.item.Item
import net.minecraft.util.registry.Registry
import nichrosia.common.identity.ID

interface ItemTag : ContentTag<Item> {
    override fun identify(entry: Item): ID {
        return ID(Registry.ITEM.getId(entry))
    }

    open class Basic(override val location: ID, vararg values: Item) : ItemTag {
        override val entries: MutableList<Item> = mutableListOf(*values)
    }
}