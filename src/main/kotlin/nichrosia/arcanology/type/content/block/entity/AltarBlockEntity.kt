package nichrosia.arcanology.type.content.block.entity

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.item.Items
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import nichrosia.arcanology.registry.category.ArcanologyCategory.arcanology
import nichrosia.arcanology.type.block.entity.BlockEntityWithBlock
import nichrosia.arcanology.type.content.block.AltarBlock
import nichrosia.registry.Registrar

open class AltarBlockEntity(pos: BlockPos, state: BlockState, override val block: AltarBlock) : BlockEntity(Registrar.arcanology.blockEntity.altar, pos, state), BlockEntityWithBlock<AltarBlock> {
    val heart = Items.TRIDENT

    @Suppress("unused_parameter")
    fun tick(world: World, pos: BlockPos, state: BlockState) {

    }

    companion object {
        fun tick(world: World, pos: BlockPos, state: BlockState, entity: AltarBlockEntity) {
            entity.tick(world, pos, state)
        }
    }
}