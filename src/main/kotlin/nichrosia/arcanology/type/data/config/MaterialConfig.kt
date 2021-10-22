package nichrosia.arcanology.type.data.config

import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.registry.properties.ExternalRegistrarProperty
import nichrosia.arcanology.type.data.MaterialHelper
import kotlin.reflect.KProperty

open class MaterialConfig<R, T : R>(
    ID: Identifier,
    val registrar: Registrar<R>,
    /** The content for this material. Content should only return null in empty & unused configurations. */
    content: MaterialHelper.(MaterialConfig<R, T>) -> T?
) : AbstractConfig<T?, MaterialConfig<R, T>>(ID, content) {
    constructor(name: String, registrar: Registrar<R>, content: MaterialHelper.(MaterialConfig<R, T>) -> T?) : this(Arcanology.idOf(name), registrar, content)

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE", "UNCHECKED_CAST")
    override fun getValue(materialHelper: MaterialHelper, property: KProperty<*>): T {
        val value by MaterialProperty(materialHelper)
        return value
    }

    @Suppress("UNCHECKED_CAST")
    open inner class MaterialProperty(val materialHelper: MaterialHelper) : ExternalRegistrarProperty<R, T>(
        registrar,
        ID,
        true,
        { content(materialHelper, this) ?: throw IllegalStateException("Attempted to access property with empty material configuration.") }
    )
}