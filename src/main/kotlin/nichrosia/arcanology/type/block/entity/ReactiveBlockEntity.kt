package nichrosia.arcanology.type.block.entity

import net.minecraft.SharedConstants
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
import nichrosia.arcanology.content.ABlockEntityTypes
import nichrosia.arcanology.content.AStatusEffects
import nichrosia.arcanology.type.status.ElementalWrathStatusEffectInstance

open class ReactiveBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(ABlockEntityTypes.reactiveBlockEntity, pos, state) {
    companion object {
        fun onBreak(pos: BlockPos, playerEntity: PlayerEntity) {
            playerEntity.removeStatusEffect(AStatusEffects.elementalWrath)
            playerEntity.addStatusEffect(ElementalWrathStatusEffectInstance(SharedConstants.TICKS_PER_SECOND * 30, pos))
        }
    }
}