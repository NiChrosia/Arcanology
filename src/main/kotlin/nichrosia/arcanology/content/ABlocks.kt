package nichrosia.arcanology.content

import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.content.type.RegisterableContent
import nichrosia.arcanology.data.DataGenerator
import nichrosia.arcanology.energy.EnergyTier
import nichrosia.arcanology.type.block.AltarBlock
import nichrosia.arcanology.type.block.PulverizerBlock
import nichrosia.arcanology.type.block.ReactiveBlock

@Suppress("MemberVisibilityCanBePrivate")
object ABlocks : RegisterableContent<Block>(Registry.BLOCK) {
    lateinit var reactiveBlock: ReactiveBlock
    lateinit var altar: AltarBlock
    lateinit var pulverizer: PulverizerBlock

    override fun load() {
        reactiveBlock = register(
            "reactive_block",
            ReactiveBlock(
                FabricBlockSettings.of(ABlockMaterials.elementalCrystal)
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

        pulverizer = register(
            "pulverizer",
            PulverizerBlock(
                FabricBlockSettings.of(Material.METAL)
                .requiresTool()
                .strength(5f, 100f)
                .breakByTool(FabricToolTags.PICKAXES, 1),
                EnergyTier.T1
            )
        )
    }

    override fun <T : Block> register(identifier: Identifier, content: T): T {
        val registeredContent = Registry.register(type, identifier, content)

        if (content is AltarBlock) {
            DataGenerator.normalBlockstate(registeredContent)
            DataGenerator.altarBlockModel(registeredContent as AltarBlock)
        } else {
            DataGenerator.normalBlockstate(registeredContent)
            DataGenerator.normalBlockModel(registeredContent)
        }

        val id = Registry.BLOCK.getId(registeredContent)
        DataGenerator.lang.block(id, generateLang(id.path))

        all.add(registeredContent)

        return registeredContent
    }
}