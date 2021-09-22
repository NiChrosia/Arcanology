package nichrosia.arcanology.type.content

import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology

@Suppress("MemberVisibilityCanBePrivate")
interface RegistryContent<T> {
    val registry: Registry<T>

    fun <E : T> register(identifier: Identifier, content: E): E {
        return Registry.register(registry, identifier, content)
    }

    fun <E : T> register(name: String, content: E): E {
        return register(Arcanology.idOf(name), content)
    }
}