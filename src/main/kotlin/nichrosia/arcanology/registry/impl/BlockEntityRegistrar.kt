package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.Arcanology.Category.arcanology
import nichrosia.arcanology.type.content.block.entity.BlockEntityWithBlock
import nichrosia.arcanology.type.content.block.entity.SmelterBlockEntity
import nichrosia.common.identity.ID
import nichrosia.common.registry.type.Registrar
import nichrosia.common.registry.type.basic.BasicContentRegistrar

open class BlockEntityRegistrar : BasicContentRegistrar<BlockEntityType<*>>() {
    val smelter by memberOf(Arcanology.identify("separator_block_entity")) { create(::SmelterBlockEntity, Registrar.arcanology.block.smelter) }

    override fun <E : BlockEntityType<*>> register(input: ID, output: E): E {
        return super.register(input, output).also {
            Registry.register(Registry.BLOCK_ENTITY_TYPE, input, output)
        }
    }

    open fun <B, E> create(entityFactory: (BlockPos, BlockState, B) -> E, block: B): BlockEntityType<E> where B : BlockWithEntity, E : BlockEntity, E : BlockEntityWithBlock<B> {
        return FabricBlockEntityTypeBuilder.create({ pos, state ->
            entityFactory(pos, state, block)
        }, block).build(null)
    }
}