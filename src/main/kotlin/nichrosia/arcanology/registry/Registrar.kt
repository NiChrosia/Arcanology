package nichrosia.arcanology.registry

import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.impl.*
import nichrosia.arcanology.registry.properties.RegistrarProperty
import nichrosia.arcanology.registry.properties.RegistryProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

/** A base registrar class for registering content. The base implementations of register functions should be done in
 * the implementation class. Additionally, it also implements [MutableMap], so it has all the properties of a map. */
@Suppress("UNCHECKED_CAST")
interface Registrar<T> : MutableMap<Identifier, T> {
    override val entries: MutableSet<MutableMap.MutableEntry<Identifier, T>>
        get() = registry.entries

    override val keys: MutableSet<Identifier>
        get() = registry.keys

    override val values: MutableCollection<T>
        get() = registry.values

    override val size: Int
        get() = registry.size

    /** The core registry which contains all of the items. It should be overridden for a custom type that registers
     * the item externally. */
    val registry: MutableMap<Identifier, T>

    /** A map of whether the specified item is fully registered. */
    val registeredMap: MutableMap<Identifier, Boolean>

    /** The default item, to use if no item is found. */
    val default: T?

    /** All RegistryProperties for this registrar. */
    val registryProperties: MutableList<RegistryProperty<T, *>>

    /** All RegistrarProperties for this registrar. */
    val registrarProperties: MutableList<RegistrarProperty<T, *>>

    /** Forcibly create all of the content declared within the fields of this class. */
    fun createAll() {
        registryProperties.forEach { it.create(this) }
        registrarProperties.forEach { it.create(this) }
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

    operator fun get(key: String): T {
        return get(Arcanology.idOf(key))
    }

    override operator fun get(key: Identifier): T {
        return registry[key] ?: default ?: throw IllegalStateException("Cannot use default, for missing registry entry, as it does not exist.")
    }

    operator fun set(path: String, value: T): T? {
        return registry.put(Arcanology.idOf(path), value)
    }

    override fun put(key: Identifier, value: T): T? {
        return registry.put(key, value)
    }

    override fun putAll(from: Map<out Identifier, T>) {
        registry.putAll(from)
    }

    /** Do nothing, as registry removal is not supported */
    override fun remove(key: Identifier): T? {
        return null
    }

    override fun containsKey(key: Identifier): Boolean {
        return registry.containsKey(key)
    }

    fun containsKey(key: String): Boolean {
        return containsKey(Arcanology.idOf(key))
    }

    override fun containsValue(value: T): Boolean {
        return registry.containsValue(value)
    }

    override fun isEmpty(): Boolean {
        return registry.isEmpty()
    }

    override fun clear() {
        registry.clear()
    }

    /** The core loader for [Registrar]s, contains a list of all registrars, as well as loading methods. */
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
        val material = MaterialRegistrar()
        val configuredFeature = ConfiguredFeatureRegistrar()

        val all: List<Registrar<*>> = this::class.memberProperties.filterIsInstance<KProperty1<Companion, *>>().map { it.get(this) }.filterIsInstance<Registrar<*>>()

        /** Forcibly create & register all of the content declared within all registries. */
        fun fullyRegisterAll() {
            all.forEach(Registrar<*>::createAll)
            all.forEach(Registrar<*>::registerAll)
        }
    }
}