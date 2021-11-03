package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.lang.LanguageGenerator
import nichrosia.arcanology.registry.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.type.content.block.BlockWithState
import nichrosia.arcanology.type.content.block.ModeledBlock
import nichrosia.arcanology.type.content.block.SmelterBlock
import nichrosia.arcanology.type.energy.EnergyTier
import nichrosia.common.identity.ID
import nichrosia.common.registry.type.basic.BasicContentRegistrar

open class BlockRegistrar : BasicContentRegistrar<Block>() {
    val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val smelter by memberOf(Arcanology.identify("smelter")) {
        SmelterBlock(
            FabricBlockSettings.of(Material.METAL)
                .requiresTool()
                .strength(5f, 100f)
                .breakByTool(FabricToolTags.PICKAXES, 1),
            EnergyTier.standard
        )
    }

    override fun <E : Block> register(input: ID, output: E): E {
        return super.register(input, output).also {
            Registry.register(Registry.BLOCK, input, it)
            Arcanology.packManager.english.lang["block.${input.namespace}.${input.path}"] = languageGenerator.generateLang(input)

            when(it) {
                is ModeledBlock -> it.generateModel(input)
                else -> ModeledBlock.generateDefaultModel(input)
            }.forEach { (ID, model) ->
                Arcanology.packManager.main.addModel(model, ID)
            }

            when(it) {
                is BlockWithState -> it.generateBlockState(input)
                else -> BlockWithState.generateDefaultBlockState(input)
            }.forEach { (ID, state) ->
                Arcanology.packManager.main.addBlockState(state, ID)
            }
        }
    }
}