package nichrosia.arcanology.registry

import nichrosia.arcanology.registry.impl.client.BlockEntityRendererRegistrar
import nichrosia.arcanology.registry.impl.client.ScreenRegistrar
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties

/** An interface purely to denote that this registrar is used on the client, as well as the companion object for declaring registrars. */
object ClientRegistrar {
    val blockEntityRenderer = BlockEntityRendererRegistrar()
    val screen = ScreenRegistrar()

    val all: List<Registrar<*>> = this::class.memberProperties.filterIsInstance<KProperty1<ClientRegistrar, *>>().map { it.get(this) }.filterIsInstance<Registrar<*>>()

    /** Forcibly create & register all of the content declared within all registries. */
    fun fullyRegisterAll() {
        all.forEach(Registrar<*>::createAll)
        all.forEach(Registrar<*>::registerAll)
    }
}