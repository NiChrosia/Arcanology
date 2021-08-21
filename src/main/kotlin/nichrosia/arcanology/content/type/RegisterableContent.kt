package nichrosia.arcanology.content.type

import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

@Suppress("MemberVisibilityCanBePrivate")
abstract class RegisterableContent<T>(open val registry: Registry<T>) : Content {
    open fun <E : T> register(identifier: Identifier, content: E): E {
        return Registry.register(registry, identifier, content)
    }

    open fun <E : T> register(name: String, content: E): E {
        return register(identify(name), content)
    }
}