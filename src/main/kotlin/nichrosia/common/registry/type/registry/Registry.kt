package nichrosia.common.registry.type.registry

interface Registry<I, O> {
    val content: MutableMap<I, O>
    val reversed: MutableMap<O, I>

    fun <E : O> register(input: I, output: E): E {
        return output.also {
            content[input] = it
            reversed[it] = input
        }
    }

    fun <E : O> identify(output: E): I? {
        return reversed[output]
    }

    fun find(input: I): O? {
        return content[input]
    }
}