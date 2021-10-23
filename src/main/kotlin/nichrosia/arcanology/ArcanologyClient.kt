package nichrosia.arcanology

import net.fabricmc.api.ClientModInitializer
import nichrosia.arcanology.registry.category.ArcanologyClientCategory.arcanologyClient
import nichrosia.registry.Registrar

object ArcanologyClient : ClientModInitializer {
    const val modID = "arcanology"

    override fun onInitializeClient() {
        Registrar.arcanologyClient.register()
    }
}