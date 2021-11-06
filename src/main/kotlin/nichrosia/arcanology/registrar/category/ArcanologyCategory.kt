package nichrosia.arcanology.registrar.category

import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registrar.impl.*
import nichrosia.common.record.category.RegistrarCategory

@Suppress("unused")
open class ArcanologyCategory : RegistrarCategory(Arcanology.identify("main")) {
    val tags by categoryOf(::TagCategory)

    val blockMaterial by registrarOf(::BlockMaterialRegistrar)
    val itemGroup by registrarOf(::ItemGroupRegistrar)
    val item by registrarOf(::ItemRegistrar)
    val block by registrarOf(::BlockRegistrar)
    val blockEntity by registrarOf(::BlockEntityRegistrar)
    val toolMaterial by registrarOf(::ToolMaterialRegistrar)
    val statusEffect by registrarOf(::StatusEffectRegistrar)
    val guiDescription by registrarOf(::GUIDescriptionRegistrar)
    val configuredFeature by registrarOf(::ConfiguredFeatureRegistrar)
    val recipeType by registrarOf(::RecipeTypeRegistrar)
    val recipeSerializer by registrarOf(::RecipeSerializerRegistrar)
    val sound by registrarOf(::SoundRegistrar)
}