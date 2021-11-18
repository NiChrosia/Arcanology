package nichrosia.arcanology.type.registar.minecraft

import net.minecraft.util.registry.Registry
import nichrosia.common.identity.ID
import nichrosia.common.record.member.MemberList
import nichrosia.common.record.registrar.content.ContentRegistrar
import nichrosia.common.record.registry.content.ContentRegistry

interface VanillaRegistrar<T> : ContentRegistrar<T> {
    val minecraftRegistry: Registry<T>

    override fun <E : T> publish(key: ID, value: E): E {
        return super.publish(key, value).also {
            Registry.register(minecraftRegistry, key, value)
        }
    }

    open class Basic<T>(override val minecraftRegistry: Registry<T>) : VanillaRegistrar<T> {
        override val registry: ContentRegistry<T> = ContentRegistry.Basic()
        override val members: MemberList<T> = MemberList.Basic()
    }
}