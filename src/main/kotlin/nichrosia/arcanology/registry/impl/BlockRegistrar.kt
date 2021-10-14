package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.registry.RegistryRegistrar
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.registry.properties.RegistrarProperty
import nichrosia.arcanology.type.block.ModeledBlock
import nichrosia.arcanology.type.block.StatedBlock
import nichrosia.arcanology.type.content.block.AltarBlock
import nichrosia.arcanology.type.content.block.ReactiveBlock
import nichrosia.arcanology.type.content.block.RuneInfuserBlock
import nichrosia.arcanology.type.content.block.SeparatorBlock
import nichrosia.arcanology.type.energy.EnergyTier

open class BlockRegistrar : RegistryRegistrar<Block>(Registry.BLOCK, "block") {
    override val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val reactiveBlock by RegistrarProperty("reactive_block") {
        ReactiveBlock(FabricBlockSettings.of(Registrar.blockMaterial.elementalCrystal)
            .requiresTool()
            .strength(5f, 500f)
            .breakByTool(FabricToolTags.PICKAXES, 3))
    }

    val altar by RegistrarProperty("altar") {
        AltarBlock(FabricBlockSettings.of(Material.STONE)
            .requiresTool()
            .strength(5f, 1000f)
            .breakByTool(FabricToolTags.PICKAXES, 4))
    }

    val separator by RegistrarProperty("separator") {
        SeparatorBlock(FabricBlockSettings.of(Material.METAL)
            .requiresTool()
            .strength(5f, 100f)
            .breakByTool(FabricToolTags.PICKAXES, 1),
            EnergyTier.standard)
    }

    val runeInfuser by RegistrarProperty("rune_infuser") {
        RuneInfuserBlock(FabricBlockSettings.of(Registrar.blockMaterial.elementalCrystal)
            .requiresTool()
            .strength(5f, 150f)
            .breakByTool(FabricToolTags.PICKAXES, 3))
    }

    @Suppress("UNCHECKED_CAST")
    override fun <E : Block> register(key: Identifier, value: E): E {
        val registered = super.register(key, value)

        when(registered) {
            is ModeledBlock -> registered.generateModel(key)
            else -> ModeledBlock.generateDefaultModel(key)
        }

        when(registered) {
            is StatedBlock -> registered.generateBlockState(key)
            else -> StatedBlock.generateDefaultBlockState(key)
        }

        return registered
    }
}