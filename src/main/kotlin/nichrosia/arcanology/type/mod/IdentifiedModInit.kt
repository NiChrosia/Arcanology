package nichrosia.arcanology.type.mod

import net.minecraft.util.Identifier

interface IdentifiedModInit {
    val modID: String

    fun idOf(path: String): Identifier {
        return Identifier(modID, path)
    }
}