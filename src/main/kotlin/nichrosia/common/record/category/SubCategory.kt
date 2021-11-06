package nichrosia.common.record.category

open class SubCategory<C : RegistrarCategory>(val provider: () -> C) : Lazy<C> by lazy(provider) {
    open fun register() {
        value.register()
    }

    open fun publish() {
        value.publish()
    }
}