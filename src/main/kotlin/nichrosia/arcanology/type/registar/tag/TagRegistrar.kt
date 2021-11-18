package nichrosia.arcanology.type.registar.tag

import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.world.data.tag.ContentTag
import nichrosia.common.identity.ID
import nichrosia.common.record.registrar.content.ContentRegistrar

interface TagRegistrar<T> : ContentRegistrar<ContentTag<T>> {
    override fun publish() {
        registry.catalog.values.forEach(Arcanology.packManager.tags::add)
    }

    fun emptyTagOf(location: ID): ContentTag<T>

    fun add(location: ID, entries: Collection<T>, fallback: ContentTag<T> = emptyTagOf(location)): ContentTag<T> {
        val tag = find(location, fallback)

        entries.forEach(tag.entries::add)

        return tag
    }

    /** Merge this [tag] with pre-existing ones, or register it if it does not exist. */
    fun merge(tag: ContentTag<T>): ContentTag<T> {
        return add(tag.location, tag.entries)
    }
}