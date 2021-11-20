package arcanology.registrar.impl

import arcanology.Arcanology
import arcanology.Arcanology.arcanology
import arcanology.type.api.common.world.block.*
import arcanology.type.common.registar.lang.VanillaLangRegistrar
import arcanology.type.common.world.block.SmelterBlock
import arcanology.type.common.world.block.settings.FluidMachineBlockSettings
import arcanology.type.common.world.data.energy.EnergyTier
import arcanology.type.common.world.data.fluid.FluidTier
import arcanology.util.data.gen.addBlockstate
import arcanology.util.data.gen.addModel
import arcanology.util.world.blocks.requiresToolExtension
import arcanology.util.world.blocks.strengthExtension
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.util.registry.Registry
import nichrosia.common.identity.ID
import nichrosia.common.record.registrar.Registrar

open class BlockContentRegistrar : VanillaLangRegistrar.Basic<Block>(Registry.BLOCK, "block") {
    val smelter by memberOf(Arcanology.identify("standard_smelter")) {
        val settings = FluidMachineBlockSettings(Material.METAL, 1, EnergyTier.standard, FluidTier.standard)
            .requiresToolExtension()
            .strengthExtension(5f, 75f)

        SmelterBlock(settings)
    }

    override fun <E : Block> register(key: ID, value: E): E {
        return super.register(key, value).also {
            if (it is ItemBlock) {
                Registrar.arcanology.item.register(key, it.item)
            }

            if (it is TaggedBlock) {
                it.tags.forEach(Registrar.arcanology.tags.blocks::merge)
            }

            if (it is EntityBlock<*>) {
                Registrar.arcanology.blockEntity.register(key, it.type)
            }
        }
    }

    override fun <E : Block> publish(key: ID, value: E): E {
        return super.publish(key, value).also {
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
        }
    }
}