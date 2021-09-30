package nichrosia.arcanology.type.data.config

import nichrosia.arcanology.type.data.MaterialHelper
import kotlin.properties.ReadOnlyProperty

abstract class AbstractConfig<T, E : AbstractConfig<T, E>>(val name: String, val content: MaterialHelper.(E) -> T) : ReadOnlyProperty<MaterialHelper, T>