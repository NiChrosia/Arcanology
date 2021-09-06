package nichrosia.arcanology.type.mod

import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier

interface IdentifiedModInitializer : ModInitializer {
    val modID: String

    fun idOf(path: String) = Identifier(modID, path)
}