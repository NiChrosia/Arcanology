package nichrosia.arcanology.registrar.category

import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registrar.impl.*
import nichrosia.common.record.category.RegistrarCategory

@Suppress("unused")
open class ArcanologyCategory : RegistrarCategory(Arcanology.identify("main")) {
    val tags by categoryOf(::TagCategory)

    val blockMaterial by registrarOf(::BlockMaterialContentRegistrar)
    val itemGroup by registrarOf(::ItemGroupContentRegistrar)
    val item by registrarOf(::ItemContentRegistrar)
    val block by registrarOf(::BlockContentRegistrar)
    val blockEntity by registrarOf(::BlockEntityContentRegistrar)
    val toolMaterial by registrarOf(::ToolMaterialContentRegistrar)
    val statusEffect by registrarOf(::StatusEffectContentRegistrar)
    val guiDescription by registrarOf(::GUIDescriptionContentRegistrar)
    val configuredFeature by registrarOf(::ConfiguredFeatureContentRegistrar)
    val recipeType by registrarOf(::RecipeTypeContentRegistrar)
    val recipeSerializer by registrarOf(::RecipeSerializerContentRegistrar)
    val sound by registrarOf(::SoundContentRegistrar)
}