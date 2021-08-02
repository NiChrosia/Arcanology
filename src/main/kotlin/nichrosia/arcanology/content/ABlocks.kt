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
    lateinit var velosiumOre: OreBlock
    lateinit var aegiriteOre: OreBlock
    lateinit var xenothiteOre: OreBlock

    lateinit var reactiveBlock: ReactiveBlock

    lateinit var altar: AltarBlock

    override fun load() {
        velosiumOre = register(
            "velosium_ore_block",
            OreBlock(
                FabricBlockSettings.of(AMaterials.velosium)
                .requiresTool()
                .strength(5f, 150f)
                .breakByTool(FabricToolTags.PICKAXES, 3)
            )
        )

        aegiriteOre = register(
            "aegirite_ore_block",
            OreBlock(
                FabricBlockSettings.of(AMaterials.elementalCrystal)
                .requiresTool()
                .strength(5f, 100f)
                .breakByTool(FabricToolTags.PICKAXES, 3)
            )
        )

        xenothiteOre = register(
            "xenothite_ore_block",
            OreBlock(
                FabricBlockSettings.of(AMaterials.xenothite)
                .requiresTool()
                .strength(5f, 250f)
                .breakByTool(FabricToolTags.PICKAXES, 4)
            )
        )

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
            "altar_block",
            AltarBlock(
                FabricBlockSettings.of(Material.STONE)
                .requiresTool()
                .strength(5f, 1000f)
                .breakByTool(FabricToolTags.PICKAXES, 4)
            )
        )
    }
}