package nichrosia.arcanology.registry

/** A [Registrar] that has no valid registry entries, as the registering does not return a value. */
open class EmptyRegistrar : BasicRegistrar<Unit>()