package nichrosia.arcanology

import net.devtech.arrp.api.RRPCallback
import net.devtech.arrp.api.RuntimeResourcePack
import net.fabricmc.api.ModInitializer
import nichrosia.arcanology.content.*

@Suppress("unused")
object Arcanology : ModInitializer {
    internal val content = arrayOf(
        AMaterials,
        ABlocks,
        ABlockEntityTypes,
        AConfiguredFeatures,
        AItemGroups,
        AItems,
        AStatusEffects
    )

    internal val resourcePack = RuntimeResourcePack.create("arcanology:main")

    const val modID = "arcanology"

    override fun onInitialize() {
        content.forEach(Content::load)

        RRPCallback.BEFORE_VANILLA.register { it.add(resourcePack) }

        resourcePack.dump()
    }
}