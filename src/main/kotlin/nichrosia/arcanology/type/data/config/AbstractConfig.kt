package nichrosia.arcanology.type.data.config

import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.type.data.MaterialHelper
import kotlin.properties.ReadOnlyProperty

abstract class AbstractConfig<T, E : AbstractConfig<T, E>>(val ID: Identifier, val content: MaterialHelper.(E) -> T) : ReadOnlyProperty<MaterialHelper, T> {
    constructor(name: String, content: MaterialHelper.(E) -> T) : this(Arcanology.idOf(name), content)
}