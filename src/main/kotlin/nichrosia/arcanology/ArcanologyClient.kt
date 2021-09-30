package nichrosia.arcanology

import net.fabricmc.api.ClientModInitializer
import nichrosia.arcanology.registry.ClientRegistrar

object ArcanologyClient : ClientModInitializer {
    override fun onInitializeClient() {
        ClientRegistrar.fullyRegisterAll()
    }
}