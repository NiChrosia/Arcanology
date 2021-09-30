package nichrosia.arcanology.util

fun String.capitalize(): String {
    return replaceFirstChar { it.uppercaseChar() }
}