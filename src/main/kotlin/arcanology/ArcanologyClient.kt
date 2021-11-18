package arcanology

import arcanology.registrar.category.ArcanologyCategory
import arcanology.registrar.category.ClientCategory
import arcanology.type.common.mod.IdentifiedMod
import net.fabricmc.api.ClientModInitializer

@Suppress("unused")
object ArcanologyClient : IdentifiedMod by Arcanology, ClientModInitializer {
    override val category = ClientCategory()

    val ArcanologyCategory.client: ClientCategory
        get() = category

    override fun onInitializeClient() {
        category.initialize()

        log.info("Client loaded successfully.")
    }
}