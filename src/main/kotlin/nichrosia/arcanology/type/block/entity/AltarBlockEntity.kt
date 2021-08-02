package nichrosia.arcanology.type.block.entity

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import nichrosia.arcanology.content.ABlockEntityTypes
import nichrosia.arcanology.type.element.ElementalHeart

open class AltarBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(ABlockEntityTypes.altarBlockEntity, pos, state) {
    var heart = ElementalHeart.Arcane

    fun tick(world: World, pos: BlockPos, state: BlockState) {

    }

    companion object {
        fun tick(world: World, pos: BlockPos, state: BlockState, entity: AltarBlockEntity) {
            entity.tick(world, pos, state)
        }
    }
}