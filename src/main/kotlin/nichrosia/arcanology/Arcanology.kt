package nichrosia.arcanology

import net.fabricmc.api.ModInitializer
import nichrosia.arcanology.registrar.category.ArcanologyCategory
import nichrosia.arcanology.type.data.runtimeresource.RuntimeResourcePackManager
import nichrosia.arcanology.type.mod.IdentifiedMod
import nichrosia.arcanology.util.capitalize
import nichrosia.common.record.registrar.Registrar
import org.apache.logging.log4j.LogManager

object Arcanology : IdentifiedMod, ModInitializer {
    override val modID = "arcanology"
    override val category = ArcanologyCategory()
    override val log = LogManager.getLogger(modID.capitalize())

    val packManager = RuntimeResourcePackManager(modID)

    val Registrar.Companion.arcanology: ArcanologyCategory
        get() = category

    override fun onInitialize() {
        category.initialize()
        packManager.load()

        log.info("${modID.capitalize()} loaded successfully.")
    }
}