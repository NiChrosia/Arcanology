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
import nichrosia.arcanology.type.content.block.BlockWithState
import nichrosia.arcanology.type.content.block.ModeledBlock
import nichrosia.arcanology.type.content.block.SeparatorBlock
import nichrosia.arcanology.type.energy.EnergyTier
import nichrosia.common.config.configure
import nichrosia.common.identity.ID
import nichrosia.registry.BasicRegistrar
import nichrosia.registry.Registrar

open class BlockRegistrar : BasicRegistrar<Block>() {
    val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val separator by memberOf(Arcanology.identify("separator")) {
        SeparatorBlock(FabricBlockSettings.of(Material.METAL)
            .requiresTool()
            .strength(5f, 100f)
            .breakByTool(FabricToolTags.PICKAXES, 1),
            EnergyTier.standard
        )
    }.configure {
        blockItemSettings = Registrar.arcanology.item.techSettings
    }

    @Suppress("UNCHECKED_CAST")
    override fun <E : Block> register(location: ID, value: E): E {
        val registered = super.register(location, value)

        Registry.register(Registry.BLOCK, location, registered)
        Arcanology.packManager.english.lang["block.${location.namespace}.${location.path}"] = languageGenerator.generateLang(location)

        when(registered) {
            is ModeledBlock -> registered.generateModel(location)
            else -> ModeledBlock.generateDefaultModel(location)
        }.forEach { (ID, model) ->
            Arcanology.packManager.main.addModel(model, ID)
        }

        when(registered) {
            is BlockWithState -> registered.generateBlockState(location)
            else -> BlockWithState.generateDefaultBlockState(location)
        }.forEach { (ID, state) ->
            Arcanology.packManager.main.addBlockState(state, ID)
        }

        return registered
    }
}