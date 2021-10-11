package nichrosia.arcanology.type.mod

import net.minecraft.util.Identifier
import nichrosia.arcanology.util.capitalize
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

abstract class IdentifiedMod {
    abstract val modID: String

    val log: Logger
        get() = LogManager.getLogger(modID.capitalize())

    fun idOf(path: String): Identifier {
        return Identifier(modID, path)
    }
}