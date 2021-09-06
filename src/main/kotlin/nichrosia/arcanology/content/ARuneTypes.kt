package nichrosia.arcanology.content

import nichrosia.arcanology.content.type.Content
import nichrosia.arcanology.ctype.rune.ManaboundRuneType
import nichrosia.arcanology.type.rune.RuneType

object ARuneTypes : Content {
    /** Uses mana instead of durability when mana is present. */
    lateinit var manabound: RuneType

    override fun load() {
        manabound = ManaboundRuneType()
    }
}