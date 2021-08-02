package nichrosia.arcanology.content

import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.type.block.entity.AltarBlockEntity
import nichrosia.arcanology.type.block.entity.ReactiveBlockEntity

object ABlockEntityTypes : RegisterableContent<BlockEntityType<*>>(Registry.BLOCK_ENTITY_TYPE) {
    lateinit var reactiveBlockEntity: BlockEntityType<ReactiveBlockEntity>
    lateinit var altarBlockEntity: BlockEntityType<AltarBlockEntity>

    override fun load() {
        reactiveBlockEntity = register(
            "reactive_block_entity",
            FabricBlockEntityTypeBuilder.create(::ReactiveBlockEntity, ABlocks.reactiveBlock).build(null)
        )

        altarBlockEntity = register(
            "altar_block_entity",
            FabricBlockEntityTypeBuilder.create(::AltarBlockEntity, ABlocks.altar).build(null)
        )
    }

}