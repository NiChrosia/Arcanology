package arcanology.type.common.mod

import nichrosia.common.identity.ID
import nichrosia.common.record.category.RegistrarCategory
import org.apache.logging.log4j.Logger

/** A simple unified interface for a mod with an ID. */
interface IdentifiedMod {
    val modID: String
    val category: RegistrarCategory
    val log: Logger

    fun identify(path: String): ID = ID(modID, path)
}