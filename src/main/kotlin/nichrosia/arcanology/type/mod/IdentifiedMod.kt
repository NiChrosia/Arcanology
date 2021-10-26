package nichrosia.arcanology.type.mod

import nichrosia.arcanology.util.capitalize
import nichrosia.common.identity.ID
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/** A simple unified interface for a mod with an ID. */
interface IdentifiedMod {
    val modID: String

    val log: Logger
        get() = LogManager.getLogger(modID.capitalize())

    fun identify(path: String): ID {
        return ID(modID, path)
    }
}