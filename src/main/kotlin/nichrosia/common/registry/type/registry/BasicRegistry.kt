package nichrosia.common.registry.type.registry

open class BasicRegistry<I, O> : Registry<I, O> {
    override val content: MutableMap<I, O> = mutableMapOf()
    override val reversed: MutableMap<O, I> = mutableMapOf()
}