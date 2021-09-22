package nichrosia.arcanology.registry.lang

import net.minecraft.util.Identifier

/** A generator for converting item ids to formatted strings. */
interface LanguageGenerator {
    /** Transform the raw ID of an item ([Identifier.path]) to a formatted English string. */
    fun generateLang(rawID: String): String

    /** Transform the raw ID of an item ([Identifier]) to a formatted English string. */
    fun generateLang(ID: Identifier): String = generateLang(ID.path)
}