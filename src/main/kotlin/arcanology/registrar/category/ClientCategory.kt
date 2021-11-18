package arcanology.registrar.category

import arcanology.ArcanologyClient
import arcanology.registrar.impl.client.BlockEntityRendererContentRegistrar
import arcanology.registrar.impl.client.FluidTextureContentRegistrar
import arcanology.registrar.impl.client.ScreenContentRegistrar
import nichrosia.common.record.category.RegistrarCategory

@Suppress("unused")
class ClientCategory : RegistrarCategory(ArcanologyClient.identify("client")) {
    val blockEntityRenderer by registrarOf(::BlockEntityRendererContentRegistrar)
    val screen by registrarOf(::ScreenContentRegistrar)
    val fluidTexture by registrarOf(::FluidTextureContentRegistrar)
}