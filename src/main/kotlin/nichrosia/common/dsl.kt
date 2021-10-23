package nichrosia.common

import net.minecraft.util.Identifier
import nichrosia.common.identity.ID

val Identifier.asID: ID
    get() = ID(namespace, path)