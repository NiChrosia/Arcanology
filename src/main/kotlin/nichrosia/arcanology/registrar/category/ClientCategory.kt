package nichrosia.arcanology.registrar.category

import nichrosia.arcanology.ArcanologyClient
import nichrosia.arcanology.registrar.impl.client.BlockEntityRendererContentRegistrar
import nichrosia.arcanology.registrar.impl.client.FluidTextureContentRegistrar
import nichrosia.arcanology.registrar.impl.client.ScreenContentRegistrar
import nichrosia.common.record.category.RegistrarCategory

@Suppress("unused")
class ClientCategory : RegistrarCategory(ArcanologyClient.identify("client")) {
    val blockEntityRenderer by registrarOf(::BlockEntityRendererContentRegistrar)
    val screen by registrarOf(::ScreenContentRegistrar)
    val fluidTexture by registrarOf(::FluidTextureContentRegistrar)
}