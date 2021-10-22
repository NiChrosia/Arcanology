package nichrosia.arcanology.type.data.config

import nichrosia.arcanology.registry.Registrar

/** An empty configuration with a `null` content return value. */
open class EmptyConfig<R, T : R>(registrar: Registrar<R>) : MaterialConfig<R, T>("", registrar, { null })