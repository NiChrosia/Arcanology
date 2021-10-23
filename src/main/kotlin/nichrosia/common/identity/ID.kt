package nichrosia.common.identity

import net.minecraft.util.Identifier

/** A simple identification class for common usage across the mod. */
open class ID(open val namespace: String, open val path: String = "") {
    val asIdentifier = Identifier(namespace, path)

    open fun namespace(transformer: (String) -> String) = ID(transformer(namespace), path)
    open fun path(transformer: (String) -> String) = ID(namespace, transformer(path))

    open fun split(separator: String = defaultSeparator) = "$namespace$separator$path"

    override fun toString() = split()

    companion object {
        const val defaultSeparator = ":"
    }
}