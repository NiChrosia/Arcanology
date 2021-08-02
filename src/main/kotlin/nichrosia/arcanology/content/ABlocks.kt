package nichrosia.arcanology.content

import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.OreBlock
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.type.block.ReactiveBlock

@Suppress("MemberVisibilityCanBePrivate")
object ABlocks : ArcanologyContent() {
    lateinit var velosiumOre: OreBlock
    lateinit var aegiriteOre: OreBlock
    lateinit var xenothiteOre: OreBlock

    lateinit var reactiveBlock: ReactiveBlock

    override fun load() {
        velosiumOre = Registry.register(
            Registry.BLOCK,
            getIdentifier("velosium_ore_block"),
            OreBlock(
                FabricBlockSettings.of(AMaterials.velosium)
                .requiresTool()
                .strength(5f, 150f)
                .breakByTool(FabricToolTags.PICKAXES, 3)
            )
        )

        aegiriteOre = Registry.register(
            Registry.BLOCK,
            getIdentifier("aegirite_ore_block"),
            OreBlock(
                FabricBlockSettings.of(AMaterials.elementalCrystal)
                    .requiresTool()
                    .strength(5f, 100f)
                    .breakByTool(FabricToolTags.PICKAXES, 3)
            )
        )

        xenothiteOre = Registry.register(
            Registry.BLOCK,
            getIdentifier("xenothite_ore_block"),
            OreBlock(
                FabricBlockSettings.of(AMaterials.xenothite)
                    .requiresTool()
                    .strength(5f, 250f)
                    .breakByTool(FabricToolTags.PICKAXES, 4)
            )
        )

        reactiveBlock = Registry.register(
            Registry.BLOCK,
            getIdentifier("reactive_block"),
            ReactiveBlock(
                FabricBlockSettings.of(AMaterials.elementalCrystal)
                    .requiresTool()
                    .strength(5f, 500f)
                    .breakByTool(FabricToolTags.PICKAXES, 3)
            )
        )
    }

    override fun getAll(): Array<Any> {
        return arrayOf(
            velosiumOre,
            aegiriteOre,
            xenothiteOre,
            reactiveBlock
        )
    }
}