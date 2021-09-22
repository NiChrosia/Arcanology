package nichrosia.arcanology.registry

import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.impl.*
import nichrosia.arcanology.registry.properties.RegistryProperty

/** A base registrar class for registering content. The base implementations of register functions should be done in
 * the implementation class. */
@Suppress("UNCHECKED_CAST")
interface Registrar<T> {
    /** The core registry which contains all of the items. It should be overridden for a custom type that registers
     * the item externally. */
    val registry: MutableMap<Identifier, T>

    /** A map of whether the specified item is fully registered. */
    val registeredMap: MutableMap<Identifier, Boolean>

    /** The default item, to use if no item is found. */
    val default: T?

    /** All RegistryProperties for this registrar. */
    val registryProperties: MutableList<RegistryProperty<T, *>>

    /** Forcibly create all of the content declared within the fields of this class. */
    fun createAll() {
        registryProperties.forEach { it.create(this) }
    }

    /** Forcibly register all of the content within the registries, rather than waiting until accessed. */
    fun registerAll() {
        registry.forEach(this::register)
    }

    /** Create the specified item. */
    fun <E : T> create(key: Identifier, value: E): E {
        this[key] = value

        return this[key] as E
    }

    /** Create the specified item. */
    fun <E : T> create(key: String, value: E) = create(Arcanology.idOf(key), value)

    /** Register the specified item to external sources. */
    fun <E : T> register(key: Identifier, value: E): E {
        registeredMap[key] = true

        return this[key] as E
    }

    /** Register the specified item to external sources. */
    fun <E : T> register(key: String, value: E) = register(Arcanology.idOf(key), value)

    operator fun get(path: String): T {
        return this[Arcanology.idOf(path)]
    }

    operator fun set(path: String, value: T) {
        this[Arcanology.idOf(path)] = value
    }

    operator fun get(id: Identifier): T {
        return registry[id] ?: default ?: throw IllegalStateException("Cannot use default, as it does not exist.")
    }

    operator fun set(id: Identifier, value: T) {
        registry[id] = value
    }

    fun containsKey(key: Identifier) = registry.containsKey(key)
    fun containsKey(key: String) = containsKey(Arcanology.idOf(key))

    companion object {
        val blockMaterial = BlockMaterialRegistrar()
        val block = BlockRegistrar()
        val blockEntity = BlockEntityRegistrar()
        val itemGroup = ItemGroupRegistrar()
        val toolMaterial = ToolMaterialRegistrar()
        val item = ItemRegistrar()
        val statusEffect = StatusEffectRegistrar()
        val rune = RuneRegistrar()
        val guiDescription = GUIDescriptionRegistrar()

        // val all = arrayOf(blockMaterial, block, blockEntity, itemGroup, toolMaterial, item, statusEffect, rune, guiDescription)
    }
}