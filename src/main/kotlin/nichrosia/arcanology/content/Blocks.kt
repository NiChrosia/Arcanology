package nichrosia.arcanology.content

import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.OreBlock
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

open class Blocks : Loadable {
    override fun load() {
        velosiumOre = Registry.register(
            Registry.BLOCK,
            Identifier("arcanology", "velosium_ore_block"),
            OreBlock(
                FabricBlockSettings.of(Materials.naturalVelosium)
                .requiresTool()
                .strength(5f, 600f)
                .breakByTool(FabricToolTags.PICKAXES, 3)
            )
        )

        darknessCrystalOre = Registry.register(
            Registry.BLOCK,
            Identifier("arcanology", "darkness_crystal_ore_block"),
            OreBlock(
                FabricBlockSettings.of(Materials.elementalCrystal)
                    .requiresTool()
                    .strength(5f, 100f)
                    .breakByTool(FabricToolTags.PICKAXES, 3)
            )
        )
    }

    companion object {
        lateinit var velosiumOre: OreBlock

        lateinit var darknessCrystalOre: OreBlock
    }
}