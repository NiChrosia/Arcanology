package nichrosia.arcanology.content

import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology

abstract class Content {
    abstract fun load()

    fun identify(name: String) = Identifier(Arcanology.modID, name)
}