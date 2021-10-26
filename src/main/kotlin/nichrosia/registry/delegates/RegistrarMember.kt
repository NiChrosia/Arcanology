package nichrosia.registry.delegates

import net.minecraft.block.Block
import nichrosia.common.config.Configured
import nichrosia.common.identity.ID
import nichrosia.registry.Registrar
import nichrosia.registry.config.MemberConfig
import nichrosia.registry.item
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST", "LeakingThis")
open class RegistrarMember<R, T : R> internal constructor(
    val location: ID,
    val provider: (ID) -> T,
    val reference: Registrar<R>
) : ReadOnlyProperty<Any?, T>, Configured<MemberConfig, RegistrarMember<R, T>> {
    override val config: MemberConfig = MemberConfig()

    val content by lazy { provider(location) }

    val registered: Boolean
        get() = reference.find(location) != null

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return content.also {
            if (!registered) register()
        }
    }
    
    open fun register() {
        reference.register(location, content)

        if (content is Block && config.createBlockItem) {
            (this as RegistrarMember<Block, out Block>).item.register()
        }
    }
}