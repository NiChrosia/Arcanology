package nichrosia.arcanology.registry.category

import nichrosia.arcanology.ArcanologyClient
import nichrosia.arcanology.registry.impl.client.BlockEntityRendererRegistrar
import nichrosia.arcanology.registry.impl.client.ScreenRegistrar
import nichrosia.common.identity.ID
import nichrosia.registry.Registrar
import nichrosia.registry.category.RegistrarCategory

object ArcanologyClientCategory : RegistrarCategory(ID(ArcanologyClient.modID)) {
    val Registrar.Companion.arcanologyClient: ArcanologyClientCategory
        get() = this@ArcanologyClientCategory

    val blockEntityRenderer = BlockEntityRendererRegistrar()
    val screen = ScreenRegistrar()
}