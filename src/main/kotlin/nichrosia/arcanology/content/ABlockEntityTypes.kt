package nichrosia.arcanology.content

import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.content.type.RegisterableContent
import nichrosia.arcanology.type.block.entity.AltarBlockEntity
import nichrosia.arcanology.type.block.entity.PulverizerBlockEntity
import nichrosia.arcanology.type.block.entity.ReactiveBlockEntity
import nichrosia.arcanology.type.block.entity.RuneInfuserBlockEntity

object ABlockEntityTypes : RegisterableContent<BlockEntityType<*>>(Registry.BLOCK_ENTITY_TYPE) {
    lateinit var reactiveBlock: BlockEntityType<ReactiveBlockEntity>
    lateinit var altar: BlockEntityType<AltarBlockEntity>
    lateinit var pulverizer: BlockEntityType<PulverizerBlockEntity>
    lateinit var runeInfuser: BlockEntityType<RuneInfuserBlockEntity>

    override fun load() {
        reactiveBlock = register(
            "reactive_block_entity",
            FabricBlockEntityTypeBuilder.create(::ReactiveBlockEntity, ABlocks.reactiveBlock).build(null)
        )

        altar = register(
            "altar_block_entity",
            FabricBlockEntityTypeBuilder.create(::AltarBlockEntity, ABlocks.altar).build(null)
        )

        pulverizer = register(
            "pulverizer_block_entity",
            FabricBlockEntityTypeBuilder.create(::PulverizerBlockEntity, ABlocks.pulverizer).build(null)
        )

        runeInfuser = register(
            "rune_infuser_block_entity",
            FabricBlockEntityTypeBuilder.create(::RuneInfuserBlockEntity, ABlocks.runeInfuser).build(null)
        )
    }
}