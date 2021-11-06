package nichrosia.arcanology.registrar.category

import nichrosia.arcanology.ArcanologyClient
import nichrosia.arcanology.registrar.impl.client.BlockEntityRendererRegistrar
import nichrosia.arcanology.registrar.impl.client.FluidTextureRegistrar
import nichrosia.arcanology.registrar.impl.client.ScreenRegistrar
import nichrosia.common.record.category.RegistrarCategory

@Suppress("unused")
class ClientCategory : RegistrarCategory(ArcanologyClient.identify("client")) {
    val blockEntityRenderer by registrarOf(::BlockEntityRendererRegistrar)
    val screen by registrarOf(::ScreenRegistrar)
    val fluidTexture by registrarOf(::FluidTextureRegistrar)
}