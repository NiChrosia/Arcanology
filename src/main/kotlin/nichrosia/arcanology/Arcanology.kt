package nichrosia.arcanology

import net.fabricmc.api.ModInitializer
import nichrosia.arcanology.registry.impl.*
import nichrosia.arcanology.type.data.RuntimeResourcePackManager
import nichrosia.arcanology.type.mod.IdentifiedMod
import nichrosia.arcanology.util.capitalize
import nichrosia.common.identity.ID
import nichrosia.common.registry.type.Registrar
import nichrosia.common.registry.type.category.RegistrarCategory
import org.apache.logging.log4j.LogManager

@Suppress("unused")
object Arcanology : IdentifiedMod, ModInitializer {
    override val modID = "arcanology"
    override val category = Category
    override val log = LogManager.getLogger(modID.capitalize())
    val packManager = RuntimeResourcePackManager(modID)

    override fun onInitialize() {
        category.register()
        packManager.load()

        log.info("${modID.capitalize()} loaded successfully.")
    }

    object Category : RegistrarCategory(ID(modID)) {
        val Registrar.Companion.arcanology: Category
            get() = this@Category

        val blockMaterial by registrarOf(::BlockMaterialRegistrar)
        val itemGroup by registrarOf(::ItemGroupRegistrar)
        val item by registrarOf(::ItemRegistrar)
        val block by registrarOf(::BlockRegistrar)
        val blockEntity by registrarOf(::BlockEntityRegistrar)
        val toolMaterial by registrarOf(::ToolMaterialRegistrar)
        val statusEffect by registrarOf(::StatusEffectRegistrar)
        val guiDescription by registrarOf(::GUIDescriptionRegistrar)
        val configuredFeature by registrarOf(::ConfiguredFeatureRegistrar)
        val recipe by registrarOf(::RecipeRegistrar)
        val sound by registrarOf(::SoundRegistrar)
    }
}