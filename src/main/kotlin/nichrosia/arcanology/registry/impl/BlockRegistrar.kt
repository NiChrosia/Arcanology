package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.category.ArcanologyCategory.arcanology
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.type.block.ModeledBlock
import nichrosia.arcanology.type.block.StatedBlock
import nichrosia.arcanology.type.content.block.AltarBlock
import nichrosia.arcanology.type.content.block.ReactiveBlock
import nichrosia.arcanology.type.content.block.RuneInfuserBlock
import nichrosia.arcanology.type.content.block.SeparatorBlock
import nichrosia.arcanology.type.energy.EnergyTier
import nichrosia.common.identity.ID
import nichrosia.registry.BasicRegistrar
import nichrosia.registry.Registrar

open class BlockRegistrar : BasicRegistrar<Block>() {
    val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val reactiveBlock by memberOf(ID(Arcanology.modID, "reactive_block")) {
        ReactiveBlock(FabricBlockSettings.of(Registrar.arcanology.blockMaterial.elementalCrystal)
            .requiresTool()
            .strength(5f, 500f)
            .breakByTool(FabricToolTags.PICKAXES, 3)
        )
    }

    val altar by memberOf(ID(Arcanology.modID, "altar")) {
        AltarBlock(FabricBlockSettings.of(Material.STONE)
            .requiresTool()
            .strength(5f, 1000f)
            .breakByTool(FabricToolTags.PICKAXES, 4)
        )
    }

    val separator by memberOf(ID(Arcanology.modID, "separator")) {
        SeparatorBlock(FabricBlockSettings.of(Material.METAL)
            .requiresTool()
            .strength(5f, 100f)
            .breakByTool(FabricToolTags.PICKAXES, 1),
            EnergyTier.standard
        )
    }

    val runeInfuser by memberOf(ID(Arcanology.modID, "rune_infuser")) {
        RuneInfuserBlock(FabricBlockSettings.of(Registrar.arcanology.blockMaterial.elementalCrystal)
            .requiresTool()
            .strength(5f, 150f)
            .breakByTool(FabricToolTags.PICKAXES, 3)
        )
    }

    @Suppress("UNCHECKED_CAST")
    override fun <E : Block> register(location: ID, value: E): E {
        val registered = super.register(location, value)

        Registry.register(Registry.BLOCK, location.asIdentifier, registered)
        Arcanology.packManager.englishLang.lang["block.${location.namespace}.${location.path}"] = languageGenerator.generateLang(location)

        when(registered) {
            is ModeledBlock -> registered.generateModel(location.asIdentifier)
            else -> ModeledBlock.generateDefaultModel(location.asIdentifier)
        }

        when(registered) {
            is StatedBlock -> registered.generateBlockState(location.asIdentifier)
            else -> StatedBlock.generateDefaultBlockState(location.asIdentifier)
        }

        return registered
    }
}