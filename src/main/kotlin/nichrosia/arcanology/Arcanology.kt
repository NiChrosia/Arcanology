package nichrosia.arcanology

import net.fabricmc.api.ModInitializer
import nichrosia.arcanology.type.data.RuntimeResourcePackManager
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.type.mod.IdentifiedMod
import nichrosia.arcanology.type.mod.RuntimeResourceMod
import nichrosia.arcanology.type.recipe.RuneRecipe

@Suppress("unused")
object Arcanology : IdentifiedMod, RuntimeResourceMod, ModInitializer {
    override val modID = "arcanology"
    override val runtimeResourceManager = RuntimeResourcePackManager("arcanology:main", "c:arcanology")

    override fun onInitialize() {
        Registrar.fullyRegisterAll()

        RuneRecipe.type
        RuneRecipe.serializer

        runtimeResourceManager.load()
    }
}