package nichrosia.arcanology.content

import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology

abstract class RegisterableContent<B>(val type: Registry<B>) : Content() {
    fun <T : B> register(identifier: Identifier, content: T): T {
        return Registry.register(type, identifier, content)
    }

    fun <T : B> register(name: String, content: T): T {
        return register(identify(name), content)
    }
}