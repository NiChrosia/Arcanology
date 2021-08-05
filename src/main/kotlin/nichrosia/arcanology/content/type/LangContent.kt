package nichrosia.arcanology.content.type

abstract class LangContent : Content() {
    open fun generateLang(name: String): String {
        return name.split("_").joinToString(" ") { it[0].uppercaseChar() + it.substring(1) }
    }

    fun capitalize(word: String) = word[0].uppercaseChar() + word.substring(1)

    companion object {
        fun capitalize(word: String) = word[0].uppercaseChar() + word.substring(1)
    }
}