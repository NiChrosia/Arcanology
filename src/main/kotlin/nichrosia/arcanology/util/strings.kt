package nichrosia.arcanology.util

/** Capitalize the given [String]. */
fun String.capitalize(): String {
    return replaceFirstChar { it.uppercaseChar() }
}