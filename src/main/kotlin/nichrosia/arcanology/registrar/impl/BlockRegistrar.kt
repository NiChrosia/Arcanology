package nichrosia.arcanology.registrar.impl

import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.Arcanology.arcanology
import nichrosia.arcanology.registrar.lang.impl.BasicLanguageGenerator
import nichrosia.arcanology.type.content.api.block.*
import nichrosia.arcanology.type.content.block.SmelterBlock
import nichrosia.arcanology.type.content.block.settings.MachineBlockSettings
import nichrosia.arcanology.type.energy.EnergyTier
import nichrosia.arcanology.type.registar.registry.VanillaRegistrar
import nichrosia.arcanology.util.addBlockstate
import nichrosia.arcanology.util.addModel
import nichrosia.common.identity.ID
import nichrosia.common.record.registrar.Registrar

open class BlockRegistrar : VanillaRegistrar.Basic<Block>(Registry.BLOCK) {
    val languageGenerator = BasicLanguageGenerator()

    val standardSmelter by memberOf(Arcanology.identify("standard_smelter")) {
        SmelterBlock(
            MachineBlockSettings(Material.METAL, 2, EnergyTier.standard)
                .requiresTool()
                .strength(5f, 100f)
        )
    }

    val primitiveSmelter by memberOf(Arcanology.identify("primitive_smelter")) {
        SmelterBlock(
            MachineBlockSettings(Material.METAL, 1, EnergyTier.primitive)
                .requiresTool()
                .strength(5f, 75f)
        )
    }

    override fun <E : Block> register(key: ID, value: E): E {
        return super.register(key, value).also {
            if (it is ItemBlock) {
                Registrar.arcanology.item.register(key, it.item)
            }

            if (it is TaggedBlock) {
                it.tags.forEach { tag ->
                    Registrar.arcanology.tags.blocks.addAll(tag.location, tag.values)
                }
            }
        }
    }

    override fun <E : Block> publish(key: ID, value: E): E {
        return super.publish(key, value).also {
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
                if (it is LootableBlock) {
                    val lootTables = it.generateLootTable(key)

                    lootTables.forEach { (ID, table) ->
                        Arcanology.packManager.main.addLootTable(ID.path { "blocks/$it" }, table)
                    }
                }
            }

            if (it is EntityBlock<*>) {
                Registrar.arcanology.blockEntity.publish(key, it.type)
            }
        }
    }
}