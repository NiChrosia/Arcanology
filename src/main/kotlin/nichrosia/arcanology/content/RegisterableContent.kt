package nichrosia.arcanology.content

import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology

abstract class RegisterableContent<B>(val type: Registry<B>) : Content() {
    val all = mutableListOf<B>()
    
    fun <T : B> register(identifier: Identifier, content: T): T {
        val registeredContent = Registry.register(type, identifier, content)

        all.add(registeredContent)

        return registeredContent
    }

    fun <T : B> register(name: String, content: T): T {
        return register(identify(name), content)
    }
}