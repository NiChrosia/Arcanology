package nichrosia.arcanology.util

/** Capitalize the given [String]. */
fun String.capitalize(capitalizeAllLetters: Boolean = false): String {
    if (split(" ").size == 1 || !capitalizeAllLetters) return replaceFirstChar { it.uppercaseChar() }

    return split(" ").joinToString(" ") { it.capitalize() }
}