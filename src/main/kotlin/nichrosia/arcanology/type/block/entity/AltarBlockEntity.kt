package nichrosia.arcanology.type.block.entity

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import nichrosia.arcanology.content.ABlockEntityTypes
import nichrosia.arcanology.content.AItems
import nichrosia.arcanology.type.item.HeartItem

open class AltarBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(ABlockEntityTypes.altarBlockEntity, pos, state) {
    lateinit var heart: HeartItem

    val heartInitialized
      get() = this::heart.isInitialized

    fun tick(world: World, pos: BlockPos, state: BlockState) {
        heart = AItems.prismatic.heart
    }

    companion object {
        fun tick(world: World, pos: BlockPos, state: BlockState, entity: AltarBlockEntity) {
            entity.tick(world, pos, state)
        }
    }
}