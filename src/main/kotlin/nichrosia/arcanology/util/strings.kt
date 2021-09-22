package nichrosia.arcanology.util

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