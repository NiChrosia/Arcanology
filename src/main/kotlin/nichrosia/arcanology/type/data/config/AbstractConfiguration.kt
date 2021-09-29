package nichrosia.arcanology.type.data.config

import nichrosia.arcanology.type.data.MaterialHelper
import kotlin.properties.ReadOnlyProperty

abstract class AbstractConfiguration<T, E : AbstractConfiguration<T, E>>(val name: String, val content: MaterialHelper.(E) -> T) : ReadOnlyProperty<MaterialHelper, T>