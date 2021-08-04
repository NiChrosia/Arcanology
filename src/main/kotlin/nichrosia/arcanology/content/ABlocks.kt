package nichrosia.arcanology.content

import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.block.OreBlock
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.type.block.AltarBlock
import nichrosia.arcanology.type.block.ReactiveBlock

@Suppress("MemberVisibilityCanBePrivate")
object ABlocks : RegisterableContent<Block>(Registry.BLOCK) {
    lateinit var reactiveBlock: ReactiveBlock

    lateinit var altar: AltarBlock

    override fun load() {
        reactiveBlock = register(
            "reactive_block",
            ReactiveBlock(
                FabricBlockSettings.of(AMaterials.elementalCrystal)
                .requiresTool()
                .strength(5f, 500f)
                .breakByTool(FabricToolTags.PICKAXES, 3)
            )
        )

        altar = register(
            "altar",
            AltarBlock(
                FabricBlockSettings.of(Material.STONE)
                .requiresTool()
                .strength(5f, 1000f)
                .breakByTool(FabricToolTags.PICKAXES, 4)
            )
        )
    }
}