package nichrosia.arcanology

import net.fabricmc.api.ClientModInitializer
import nichrosia.arcanology.registry.ClientRegistrar
import nichrosia.arcanology.registry.Registrar
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

object ArcanologyClient : ClientModInitializer {
    override fun onInitializeClient() {
        ClientRegistrar.fullyRegisterAll()
    }
}