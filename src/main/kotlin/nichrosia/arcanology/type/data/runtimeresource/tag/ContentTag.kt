package nichrosia.arcanology.type.data.runtimeresource.tag

import net.devtech.arrp.json.tags.JTag
import net.minecraft.tag.Tag
import net.minecraft.util.Identifier
import nichrosia.common.identity.ID

interface ContentTag<T> : Tag.Identified<T> {
    val location: ID
    val values: MutableList<T>

    fun identify(entry: T): ID

    override fun values(): MutableList<T> {
        return values
    }

    override fun getId(): Identifier {
        return location
    }

    override fun contains(entry: T): Boolean {
        return values.contains(entry)
    }

    fun apply(tag: JTag): JTag {
        values.forEach {
            val location = identify(it)

            tag.add(location)
        }

        return tag
    }
}