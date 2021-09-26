package nichrosia.arcanology

import net.fabricmc.api.ClientModInitializer
import nichrosia.arcanology.registry.ClientRegistrar
import nichrosia.arcanology.registry.Registrar
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

object ArcanologyClient : ClientModInitializer {
    internal val registrars = ClientRegistrar.Companion::class.memberProperties.filterIsInstance<KProperty1<ClientRegistrar.Companion, Registrar<*>>>().map { it.get(ClientRegistrar) }

    override fun onInitializeClient() {
        registrars.forEach { it.createAll(); it.registerAll() }
    }
}