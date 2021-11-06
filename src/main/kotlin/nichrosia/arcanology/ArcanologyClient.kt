package nichrosia.arcanology

import net.fabricmc.api.ClientModInitializer
import nichrosia.arcanology.registrar.category.ArcanologyCategory
import nichrosia.arcanology.registrar.category.ClientCategory
import nichrosia.arcanology.type.mod.IdentifiedMod
import nichrosia.arcanology.util.capitalize
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object ArcanologyClient : IdentifiedMod, ClientModInitializer {
    override val modID = "arcanology"
    override val category = ClientCategory()
    override val log: Logger = LogManager.getLogger(modID.capitalize())

    @Suppress("unused")
    val ArcanologyCategory.client: ClientCategory
        get() = category

    override fun onInitializeClient() {
        category.initialize()

        log.info("${modID.capitalize()} (client) loaded successfully.")
    }
}