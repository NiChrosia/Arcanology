package nichrosia.arcanology.content

import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology

abstract class ArcanologyContent {
    abstract fun load()
    abstract fun getAll(): Array<Any>

    fun getIdentifier(name: String) = Identifier(Arcanology.modID, name)
}