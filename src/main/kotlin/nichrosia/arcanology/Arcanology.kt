package nichrosia.arcanology

import net.fabricmc.api.ModInitializer
import nichrosia.arcanology.registry.category.ArcanologyCategory.arcanology
import nichrosia.arcanology.type.data.RuntimeResourcePackManager
import nichrosia.arcanology.type.mod.IdentifiedMod
import nichrosia.arcanology.util.capitalize
import nichrosia.registry.Registrar

@Suppress("unused")
object Arcanology : IdentifiedMod, ModInitializer {
    override val modID = "arcanology"
    val packManager = RuntimeResourcePackManager(modID)

    override fun onInitialize() {
        Registrar.arcanology.register()
        packManager.load()

        log.info("${modID.capitalize()} loaded successfully.")
    }
}