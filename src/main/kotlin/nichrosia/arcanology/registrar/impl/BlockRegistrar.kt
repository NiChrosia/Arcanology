package nichrosia.arcanology.registrar.impl

import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.Arcanology.arcanology
import nichrosia.arcanology.registrar.lang.LanguageGenerator
import nichrosia.arcanology.registrar.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.type.content.block.*
import nichrosia.arcanology.type.content.block.settings.ArcanologyBlockSettings
import nichrosia.arcanology.type.energy.EnergyTier
import nichrosia.arcanology.type.registar.registry.VanillaRegistrar
import nichrosia.arcanology.util.addBlockstate
import nichrosia.arcanology.util.addModel
import nichrosia.common.identity.ID
import nichrosia.common.record.registrar.Registrar

open class BlockRegistrar : VanillaRegistrar.Basic<Block>(Registry.BLOCK) {
    val languageGenerator: LanguageGenerator = BasicLanguageGenerator()

    val smelter by memberOf(Arcanology.identify("smelter")) {
        SmelterBlock(
            ArcanologyBlockSettings(Material.METAL, 2)
                .requiresTool()
                .strength(5f, 100f)
                as ArcanologyBlockSettings,
            EnergyTier.standard
        )
    }

    override fun <E : Block> register(key: ID, value: E): E {
        return super.register(key, value).also {
            if (it is TaggedBlock) {
                it.tags.forEach { tag ->
                    Registrar.arcanology.tags.blocks.addAll(tag.location, tag.values)
                }
            }
        }
    }

    override fun <E : Block> publish(key: ID, value: E, registerIfAbsent: Boolean): E {
        return super.publish(key, value, registerIfAbsent).also {
            Arcanology.packManager.english.lang["block.${key.namespace}.${key.path}"] = languageGenerator.generateLang(key)

            val models = when(it) {
                is ModeledBlock -> it.generateModel(key)
                else -> ModeledBlock.generateDefaultModel(key)
            }

            val blockstates = when(it) {
                is MultistateBlock -> it.generateBlockstate(key)
                else -> MultistateBlock.generateDefaultBlockstate(key)
            }

            models.forEach(Arcanology.packManager.main::addModel)
            blockstates.forEach(Arcanology.packManager.main::addBlockstate)

            if (it is ItemBlock) {
                Registrar.arcanology.item.publish(key, it.item, true)

                if (it is LootableBlock) {
                    val lootTables = it.generateLootTable(key, key)

                    lootTables.forEach { (ID, table) ->
                        Arcanology.packManager.main.addLootTable(ID.path { "blocks/$it" }, table)
                    }
                }
            }
        }
    }
}