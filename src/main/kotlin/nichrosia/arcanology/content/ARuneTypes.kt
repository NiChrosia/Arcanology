package nichrosia.arcanology.content

import nichrosia.arcanology.content.type.Content
import nichrosia.arcanology.type.rune.base.RuneType
import nichrosia.arcanology.type.rune.ManaboundRuneType

object ARuneTypes : Content() {
    /** Uses mana instead of durability when mana is present. */
    lateinit var manabound: RuneType

    override fun load() {
        manabound = ManaboundRuneType()
    }
}