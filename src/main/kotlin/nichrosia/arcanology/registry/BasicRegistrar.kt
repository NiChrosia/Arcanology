package nichrosia.arcanology.registry

import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.properties.ExternalRegistrarProperty
import nichrosia.arcanology.registry.properties.RegistrarProperty

/** A basic [Registrar] implementation. */
abstract class BasicRegistrar<T> : Registrar<T> {
    override val registry = MapRegistry()
    override val registeredMap: MutableMap<Identifier, Boolean> = mutableMapOf()
    override val registrarProperties: MutableList<RegistrarProperty<T, *>> = mutableListOf()
    override val externalRegistrarProperties: MutableList<ExternalRegistrarProperty<T, *>> = mutableListOf()
    override val default: T? = null

    /** An inner class used to register the item in vanilla, as well as add a lang entry for it. */
    open inner class MapRegistry : LinkedHashMap<Identifier, T>() {
        operator fun set(key: String, value: T): T? {
            return put(Arcanology.idOf(key), value)
        }

        override fun put(key: Identifier, value: T): T? {
            registeredMap[key] = false

            return super.put(key, value)
        }
    }
}