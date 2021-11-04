package nichrosia.arcanology

import net.fabricmc.api.ClientModInitializer
import nichrosia.arcanology.registry.impl.client.BlockEntityRendererRegistrar
import nichrosia.arcanology.registry.impl.client.FluidTextureRegistrar
import nichrosia.arcanology.registry.impl.client.ScreenRegistrar
import nichrosia.arcanology.type.mod.IdentifiedMod
import nichrosia.arcanology.util.capitalize
import nichrosia.common.identity.ID
import nichrosia.common.registry.type.category.RegistrarCategory
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object ArcanologyClient : IdentifiedMod, ClientModInitializer {
    override val modID = "arcanology"
    override val category = Category
    override val log: Logger = LogManager.getLogger(modID.capitalize())

    override fun onInitializeClient() {
        category.register()

        log.info("${modID.capitalize()} (client) loaded successfully.")
    }

    @Suppress("unused")
    object Category : RegistrarCategory(ID(modID)) {
        val Arcanology.Category.client: Category
            get() = this@Category

        val blockEntityRenderer by registrarOf(::BlockEntityRendererRegistrar)
        val screen by registrarOf(::ScreenRegistrar)
        val fluidTexture by registrarOf(::FluidTextureRegistrar)
    }
}