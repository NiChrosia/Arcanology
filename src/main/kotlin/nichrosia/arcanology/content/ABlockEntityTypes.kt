package nichrosia.arcanology.content

import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.type.blockentity.ReactiveBlockEntity

object ABlockEntityTypes : ArcanologyContent() {
    lateinit var reactiveBlockEntity: BlockEntityType<ReactiveBlockEntity>

    override fun load() {
        reactiveBlockEntity = Registry.register(
            Registry.BLOCK_ENTITY_TYPE,
            getIdentifier("reactive_block_entity"),
            FabricBlockEntityTypeBuilder.create(::ReactiveBlockEntity, ABlocks.reactiveBlock).build(null)
        )
    }

    override fun getAll(): Array<Any> {
        return arrayOf(
            reactiveBlockEntity
        )
    }
}