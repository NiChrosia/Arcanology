package nichrosia.arcanology.type.registar.tag

import nichrosia.arcanology.type.data.runtimeresource.tag.ContentTag
import nichrosia.common.identity.ID
import nichrosia.common.record.registrar.content.ContentRegistrar

interface TagRegistrar<T> : ContentRegistrar<ContentTag<T>> {
    fun emptyTagOf(location: ID): ContentTag<T>

    fun addAll(location: ID, entries: Collection<T>, createIfAbsent: Boolean = true): ContentTag<T> {
        val tag = find(location, emptyTagOf(location))

        entries.forEach(tag.values::add)

        return tag
    }
}