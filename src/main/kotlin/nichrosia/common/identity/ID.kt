package nichrosia.common.identity

import net.minecraft.util.Identifier

/** A simple identification class for common usage across the mod. */
open class ID(namespace: String, path: String = "") : Identifier(namespace, path) {
    constructor(identifier: Identifier) : this(identifier.namespace, identifier.path)

    open fun namespace(transformer: (String) -> String) = ID(transformer(namespace), path)
    open fun path(transformer: (String) -> String) = ID(namespace, transformer(path))

    open fun split(separator: String = defaultSeparator) = "$namespace$separator$path"

    override fun toString() = split()

    companion object {
        const val defaultSeparator = ":"

        fun deserialize(source: String, separator: String = defaultSeparator): ID {
            val (namespace, path) = source.split(separator)

            return ID(namespace, path)
        }
    }
}