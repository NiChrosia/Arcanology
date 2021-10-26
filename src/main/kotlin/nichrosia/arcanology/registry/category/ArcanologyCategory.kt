package nichrosia.arcanology.registry.category

import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.impl.*
import nichrosia.common.identity.ID
import nichrosia.registry.Registrar
import nichrosia.registry.category.RegistrarCategory

object ArcanologyCategory : RegistrarCategory(ID(Arcanology.modID)) {
    val Registrar.Companion.arcanology: ArcanologyCategory
        get() = this@ArcanologyCategory

    val blockMaterial = BlockMaterialRegistrar()
    val itemGroup = ItemGroupRegistrar()
    val item = ItemRegistrar()
    val block = BlockRegistrar()
    val blockEntity = BlockEntityRegistrar()
    val toolMaterial = ToolMaterialRegistrar()
    val statusEffect = StatusEffectRegistrar()
    val guiDescription = GUIDescriptionRegistrar()
    val configuredFeature = ConfiguredFeatureRegistrar()
    val recipe = RecipeRegistrar()
    val sound = SoundRegistrar()
}