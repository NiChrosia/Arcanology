package nichrosia.arcanology.type.content.block.entity

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import nichrosia.arcanology.type.content.block.AltarBlock
import nichrosia.arcanology.type.content.item.magic.HeartItem
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.type.block.entity.BlockEntityWithBlock

open class AltarBlockEntity(pos: BlockPos, state: BlockState, override val block: AltarBlock) : BlockEntity(Registrar.blockEntity.altar, pos, state), BlockEntityWithBlock<AltarBlock> {
    lateinit var heart: HeartItem

    val heartInitialized
      get() = this::heart.isInitialized

    @Suppress("unused_parameter")
    fun tick(world: World, pos: BlockPos, state: BlockState) {
        heart = Registrar.material.prismatic.magicHeart
    }

    companion object {
        fun tick(world: World, pos: BlockPos, state: BlockState, entity: AltarBlockEntity) {
            entity.tick(world, pos, state)
        }
    }
}