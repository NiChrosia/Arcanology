package nichrosia.arcanology.type.data.config

import nichrosia.arcanology.type.data.MaterialHelper
import nichrosia.arcanology.registry.Registrar

/** An empty configuration with a `null` content return value. */
open class EmptyConfig<R, T : R>(
    name: String,
    registrar: Registrar<R>,
    runOnCreate: MaterialHelper.(T) -> Unit
) : MaterialConfig<R, T>(name, registrar, runOnCreate, { null })