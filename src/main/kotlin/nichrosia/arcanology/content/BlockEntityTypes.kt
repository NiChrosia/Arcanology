package nichrosia.arcanology.content

import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.type.blockentity.ReactiveBlockEntity

open class BlockEntityTypes : Loadable {
    override fun load() {
        reactiveBlockEntity = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            Identifier("arcanology", "reactive_block_entity"),
            FabricBlockEntityTypeBuilder.create(::ReactiveBlockEntity, Blocks.reactiveBlock).build(null)
        )
    }

    companion object {
        lateinit var reactiveBlockEntity: BlockEntityType<ReactiveBlockEntity>
    }
}