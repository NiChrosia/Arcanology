package nichrosia.arcanology.registry

import nichrosia.registry.BasicRegistrar
import nichrosia.registry.Registrar

/** A [Registrar] that has no valid registry entries, as the registering does not return a value. */
open class EmptyRegistrar : BasicRegistrar<Unit>()