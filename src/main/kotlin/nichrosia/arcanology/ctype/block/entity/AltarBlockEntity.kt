package nichrosia.arcanology.ctype.block.entity

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import nichrosia.arcanology.content.AMaterials
import nichrosia.arcanology.ctype.block.AltarBlock
import nichrosia.arcanology.ctype.item.magic.HeartItem
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.type.block.entity.BlockEntityWithBlock

open class AltarBlockEntity(pos: BlockPos, state: BlockState, override val block: AltarBlock) : BlockEntity(Registrar.blockEntity.altar, pos, state), BlockEntityWithBlock<AltarBlock> {
    lateinit var heart: HeartItem

    val heartInitialized
      get() = this::heart.isInitialized

    @Suppress("unused_parameter")
    fun tick(world: World, pos: BlockPos, state: BlockState) {
        heart = AMaterials.prismatic.heart
    }

    companion object {
        fun tick(world: World, pos: BlockPos, state: BlockState, entity: AltarBlockEntity) {
            entity.tick(world, pos, state)
        }
    }
}