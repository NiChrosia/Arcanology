package nichrosia.arcanology.registry

import nichrosia.arcanology.registry.impl.client.BlockEntityRendererRegistrar
import nichrosia.arcanology.registry.impl.client.ScreenRegistrar

/** An interface purely to denote that this registrar is used on the client, as well as the companion object for declaring registrars. */
interface ClientRegistrar {
    companion object {
        val blockEntityRenderer = BlockEntityRendererRegistrar()
        val screen = ScreenRegistrar()

        val all = arrayOf(blockEntityRenderer, screen)
    }
}