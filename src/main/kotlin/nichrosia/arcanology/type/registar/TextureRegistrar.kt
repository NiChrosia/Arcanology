package nichrosia.arcanology.type.registar

import nichrosia.common.registry.type.lazy.BasicLazyRegistrar

abstract class TextureRegistrar<I, O> : BasicLazyRegistrar<I, O>() {
    open val defaultWidth = 16
    open val defaultHeight = 16

    abstract fun convert(input: I, width: Int, height: Int): O?

    override fun convert(input: I): O? {
        return convert(input, defaultWidth, defaultHeight)
    }

    open fun findSized(input: I, width: Int = defaultWidth, height: Int = defaultHeight): O? {
        val registryResult = registry.find(input)

        return registryResult ?: run {
            val converted = convert(input, width, height)

            converted?.let {
                register(input, it)
            }
        }
    }
}