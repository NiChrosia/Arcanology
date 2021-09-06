package nichrosia.arcanology.content.type

import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology

/** A general wrapper for all content classes, with helper methods for [Identifier]s, automatically generating lang
 * files, and an abstract load method.
 * @author NiChrosia */
interface Content {
    fun load()

    fun identify(name: String): Identifier {
        return Arcanology.idOf(name)
    }

    fun String.capitalize(): String {
        return replaceFirstChar { it.uppercaseChar() }
    }

    fun List<String>.joinToStringIndexed(separator: String, transformer: (Int, String) -> String): String {
        return mapIndexed { index, element -> transformer(index, element) }.joinToString(separator)
    }

    fun String.mapWords(transformer: (String) -> String): String {
        return split(" ").joinToString(" ") { transformer(it) }
    }

    fun String.mapWordsIndexed(transformer: (Int, String) -> String): String {
        return split(" ").joinToStringIndexed(" ") { index, element -> transformer(index, element) }
    }

    fun generateLang(name: String): String {
        return name.replace("_", " ").mapWords { it.capitalize() }
    }
}