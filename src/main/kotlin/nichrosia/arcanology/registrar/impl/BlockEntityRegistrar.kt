package nichrosia.arcanology.registrar.impl

import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.Arcanology.arcanology
import nichrosia.arcanology.type.content.block.entity.BlockEntityWithBlock
import nichrosia.arcanology.type.content.block.entity.SmelterBlockEntity
import nichrosia.arcanology.type.registar.registry.VanillaRegistrar
import nichrosia.common.record.registrar.Registrar

open class BlockEntityRegistrar : VanillaRegistrar.Basic<BlockEntityType<*>>(Registry.BLOCK_ENTITY_TYPE) {
    val smelter by memberOf(Arcanology.identify("separator_block_entity")) { create(::SmelterBlockEntity, Registrar.arcanology.block.smelter) }

    open fun <B, E> create(entityFactory: (BlockPos, BlockState, B) -> E, block: B): BlockEntityType<E> where B : BlockWithEntity, E : BlockEntity, E : BlockEntityWithBlock<B> {
        return FabricBlockEntityTypeBuilder.create({ pos, state ->
            entityFactory(pos, state, block)
        }, block).build(null)
    }
}