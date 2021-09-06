package nichrosia.arcanology

import net.devtech.arrp.api.RuntimeResourcePack
import net.fabricmc.api.ModInitializer
import nichrosia.arcanology.content.*
import nichrosia.arcanology.content.type.Content
import nichrosia.arcanology.data.DataGenerator
import nichrosia.arcanology.type.mod.IdentifiedModInitializer

@Suppress("unused")
object Arcanology : IdentifiedModInitializer {
    internal val content = arrayOf(
        AScreenHandlers,
        ABlockMaterials,
        ABlocks,
        ABlockEntityTypes,
        AConfiguredFeatures,
        AItemGroups,
        AItems,
        ARunes,
        AStatusEffects,
        ARuneTypes,
        AToolMaterials,
        AMaterials,
        ARecipes,

        DataGenerator
    )

    internal val resourcePack = RuntimeResourcePack.create("arcanology:main")
    internal val commonResourcePack = RuntimeResourcePack.create("c:arcanology")

    override val modID = "arcanology"

    override fun onInitialize() {
        content.forEach(Content::load)
    }
}