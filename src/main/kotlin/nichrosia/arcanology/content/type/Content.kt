package nichrosia.arcanology.content.type

import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology

abstract class Content {
    abstract fun load()

    open fun identify(name: String) = Identifier(Arcanology.modID, name)

    open fun generateLang(name: String): String {
        return name.split("_").joinToString(" ") { it[0].uppercaseChar() + it.substring(1) }
    }

    fun capitalize(word: String) = word[0].uppercaseChar() + word.substring(1)
}