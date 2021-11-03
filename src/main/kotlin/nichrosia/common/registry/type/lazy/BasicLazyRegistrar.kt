package nichrosia.common.registry.type.lazy

import nichrosia.common.registry.type.registry.BasicRegistry

abstract class BasicLazyRegistrar<I, O> : LazyRegistrar<I, O> {
    override val registry: BasicRegistry<I, O> = BasicRegistry()
}