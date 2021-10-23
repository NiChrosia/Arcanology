package nichrosia.arcanology

import net.fabricmc.api.ModInitializer
import nichrosia.arcanology.registry.category.ArcanologyCategory.arcanology
import nichrosia.arcanology.type.data.RuntimeResourcePackManager
import nichrosia.arcanology.type.mod.IdentifiedMod
import nichrosia.arcanology.type.mod.RuntimeResourceMod
import nichrosia.arcanology.util.capitalize
import nichrosia.registry.Registrar

@Suppress("unused")
object Arcanology : IdentifiedMod(), RuntimeResourceMod, ModInitializer {
    override val modID = "arcanology"
    override val packManager = RuntimeResourcePackManager("arcanology:main", "c:arcanology")

    override fun onInitialize() {
        Registrar.arcanology.register()

        packManager.load()

        log.info("${modID.capitalize()} loaded successfully.")
    }
}