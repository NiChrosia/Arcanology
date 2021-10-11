package nichrosia.arcanology

import net.fabricmc.api.ModInitializer
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.type.data.RuntimeResourcePackManager
import nichrosia.arcanology.type.mod.IdentifiedMod
import nichrosia.arcanology.type.mod.RuntimeResourceMod

@Suppress("unused")
object Arcanology : IdentifiedMod(), RuntimeResourceMod, ModInitializer {
    override val modID = "arcanology"
    override val packManager = RuntimeResourcePackManager("arcanology:main", "c:arcanology")

    override fun onInitialize() {
        Registrar.fullyRegisterAll()

        packManager.load()

        log.info("Mod loaded successfully.")
    }
}