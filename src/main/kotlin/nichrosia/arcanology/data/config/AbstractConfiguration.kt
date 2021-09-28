package nichrosia.arcanology.data.config

import nichrosia.arcanology.data.MaterialHelper
import kotlin.properties.ReadOnlyProperty

abstract class AbstractConfiguration<T, E : AbstractConfiguration<T, E>>(val name: String, val content: MaterialHelper.(E) -> T) : ReadOnlyProperty<MaterialHelper, T>