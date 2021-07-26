package nichrosia.arcanology.content

import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.ChestBlock
import net.minecraft.block.OreBlock
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.type.block.ReactiveBlock

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

        aegiriteOre = Registry.register(
            Registry.BLOCK,
            Identifier("arcanology", "aegirite_ore_block"),
            OreBlock(
                FabricBlockSettings.of(Materials.elementalCrystal)
                    .requiresTool()
                    .strength(5f, 100f)
                    .breakByTool(FabricToolTags.PICKAXES, 3)
            )
        )

        reactiveBlock = Registry.register(
            Registry.BLOCK,
            Identifier("arcanology", "reactive_block"),
            ReactiveBlock(
                FabricBlockSettings.of(Materials.elementalCrystal)
                    .requiresTool()
                    .strength(5f, 500f)
                    .breakByTool(FabricToolTags.PICKAXES, 3)
            )
        )
    }

    companion object {
        lateinit var velosiumOre: OreBlock

        lateinit var aegiriteOre: OreBlock

        lateinit var reactiveBlock: ReactiveBlock
    }
}