package nichrosia.arcanology.type.data.config

import nichrosia.arcanology.type.data.MaterialHelper
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.registry.properties.ExternalRegistrarProperty
import kotlin.reflect.KProperty

open class MaterialConfig<R, T : R>(
    name: String,
    val registrar: Registrar<R>,
    val runOnCreate: MaterialHelper.(T) -> Unit = {},
    /** The content for this material. Content should only return null in empty & unused configurations. */
    content: MaterialHelper.(MaterialConfig<R, T>) -> T?
) : AbstractConfig<T?, MaterialConfig<R, T>>(name, content) {
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE", "UNCHECKED_CAST")
    override fun getValue(materialHelper: MaterialHelper, property: KProperty<*>): T {
        val value by MaterialProperty(materialHelper)
        return value
    }

    @Suppress("UNCHECKED_CAST")
    open inner class MaterialProperty(val materialHelper: MaterialHelper) : ExternalRegistrarProperty<R, T>(registrar, name, true, { content(materialHelper, this) ?: throw IllegalStateException("Attempted to access property with empty material configuration.") }) {
        override fun create(thisRef: Registrar<R>) {
            super.create(thisRef)

            if (isCreated) runOnCreate(materialHelper, registrar[ID] as T)
        }
    }
}