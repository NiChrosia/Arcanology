package nichrosia.arcanology.registrar.impl

import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.type.api.world.block.entity.BlockEntityWithBlock
import nichrosia.arcanology.type.registar.minecraft.VanillaRegistrar

open class BlockEntityContentRegistrar : VanillaRegistrar.Basic<BlockEntityType<*>>(Registry.BLOCK_ENTITY_TYPE) {
    open fun <B, E> create(entityFactory: (BlockPos, BlockState, B) -> E, block: B): BlockEntityType<E> where B : BlockWithEntity, E : BlockEntity, E : BlockEntityWithBlock<B> {
        return FabricBlockEntityTypeBuilder.create({ pos, state ->
            entityFactory(pos, state, block)
        }, block).build()
    }
}