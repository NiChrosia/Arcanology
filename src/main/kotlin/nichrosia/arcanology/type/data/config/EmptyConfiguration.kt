package nichrosia.arcanology.type.data.config

import nichrosia.arcanology.type.data.MaterialHelper
import nichrosia.arcanology.registry.Registrar

/** An empty configuration with a `null` content return value. */
open class EmptyConfiguration<R, T : R>(
    name: String,
    registrar: Registrar<R>,
    runOnCreate: MaterialHelper.(T) -> Unit
) : MaterialConfiguration<R, T>(name, registrar, runOnCreate, { null })