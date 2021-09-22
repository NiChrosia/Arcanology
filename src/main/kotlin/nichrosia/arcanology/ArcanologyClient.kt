package nichrosia.arcanology

import net.fabricmc.api.ClientModInitializer
import nichrosia.arcanology.registry.ClientRegistrar
import nichrosia.arcanology.registry.Registrar

object ArcanologyClient : ClientModInitializer {
    override fun onInitializeClient() {
        ClientRegistrar.all.forEach(Registrar<*>::registerAll)
    }
}