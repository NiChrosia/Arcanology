package nichrosia.arcanology.registry

import net.minecraft.util.Identifier
import net.minecraft.util.registry.DefaultedRegistry
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.lang.LanguageGenerator

/** An abstract implementation of [Registrar] that uses a [DefaultedRegistry] from vanilla. */
abstract class RegistryRegistrar<T>(val minecraftRegistry: Registry<T>, val serializationType: String) : BasicRegistrar<T>() {
    /** The language generator for translating the ID to formatted English. This should be used in the overridden [registry]. */
    abstract val languageGenerator: LanguageGenerator

    @Suppress("USELESS_CAST") // Needed to prevent overload ambiguity
    override val default: T? = if (minecraftRegistry is DefaultedRegistry<T>) minecraftRegistry[null as Identifier?] else null

    /** Register the specified item. Used for registering in vanilla & adding lang. */
    override fun <E : T> register(key: Identifier, value: E): E {
        val registered = super.register(key, value)

        Registry.register(minecraftRegistry, key, registered)
        Arcanology.runtimeResourceManager.englishLang.lang["$serializationType.${key.namespace}.${key.path}"] = languageGenerator.generateLang(key)

        return registered
    }
}