package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.block.Material
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.ctype.block.AltarBlock
import nichrosia.arcanology.ctype.block.PulverizerBlock
import nichrosia.arcanology.ctype.block.ReactiveBlock
import nichrosia.arcanology.ctype.block.RuneInfuserBlock
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.registry.RegistryRegistrar
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.registry.properties.RegistryProperty
import nichrosia.arcanology.type.energy.EnergyTier
import nichrosia.arcanology.util.altarBlockModel
import nichrosia.arcanology.util.normalBlockModel
import nichrosia.arcanology.util.normalBlockstate

open class BlockRegistrar : RegistryRegistrar<Block>(Registry.BLOCK, "block") {
    override val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val reactiveBlock by RegistryProperty("reactive_block") {
        ReactiveBlock(FabricBlockSettings.of(Registrar.blockMaterial.elementalCrystal)
            .requiresTool()
            .strength(5f, 500f)
            .breakByTool(FabricToolTags.PICKAXES, 3))
    }

    val altar by RegistryProperty("altar") {
        AltarBlock(FabricBlockSettings.of(Material.STONE)
            .requiresTool()
            .strength(5f, 1000f)
            .breakByTool(FabricToolTags.PICKAXES, 4))
    }

    val pulverizer by RegistryProperty("pulverizer") {
        PulverizerBlock(FabricBlockSettings.of(Material.METAL)
            .requiresTool()
            .strength(5f, 100f)
            .breakByTool(FabricToolTags.PICKAXES, 1),
            EnergyTier.T1)
    }

    val runeInfuser by RegistryProperty("rune_infuser") {
        RuneInfuserBlock(FabricBlockSettings.of(Registrar.blockMaterial.elementalCrystal)
            .requiresTool()
            .strength(5f, 150f)
            .breakByTool(FabricToolTags.PICKAXES, 3))
    }

    override fun <E : Block> register(key: Identifier, value: E): E {
        val registered = super.register(key, value)

        normalBlockstate(registered)

        if (registered is AltarBlock) altarBlockModel(registered) else normalBlockModel(registered)

        return registered
    }
}