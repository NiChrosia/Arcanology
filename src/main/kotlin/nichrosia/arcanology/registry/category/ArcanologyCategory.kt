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
    val block = BlockRegistrar()
    val blockEntity = BlockEntityRegistrar()
    val itemGroup = ItemGroupRegistrar()
    val toolMaterial = ToolMaterialRegistrar()
    val item = ItemRegistrar()
    val statusEffect = StatusEffectRegistrar()
    val rune = RuneRegistrar()
    val guiDescription = GUIDescriptionRegistrar()
    val configuredFeature = ConfiguredFeatureRegistrar()
    val recipe = RecipeRegistrar()
    val sound = SoundRegistrar()
}