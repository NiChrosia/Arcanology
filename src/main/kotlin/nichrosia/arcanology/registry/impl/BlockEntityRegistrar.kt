package nichrosia.arcanology.registry.impl

import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.category.ArcanologyCategory.arcanology
import nichrosia.arcanology.type.content.block.entity.BlockEntityWithBlock
import nichrosia.arcanology.type.content.block.entity.SeparatorBlockEntity
import nichrosia.common.identity.ID
import nichrosia.registry.BasicRegistrar
import nichrosia.registry.Registrar

open class BlockEntityRegistrar : BasicRegistrar<BlockEntityType<*>>() {
    val separator by memberOf(Arcanology.identify("separator_block_entity")) { create(::SeparatorBlockEntity, Registrar.arcanology.block.separator) }

    override fun <E : BlockEntityType<*>> register(location: ID, value: E): E {
        return super.register(location, value).also {
            Registry.register(Registry.BLOCK_ENTITY_TYPE, location, value)
        }
    }

    open fun <B, E> create(entityFactory: (BlockPos, BlockState, B) -> E, block: B): BlockEntityType<E> where B : BlockWithEntity, E : BlockEntity, E : BlockEntityWithBlock<B> {
        return FabricBlockEntityTypeBuilder.create({ pos, state ->
            entityFactory(pos, state, block)
        }, block).build(null)
    }
}