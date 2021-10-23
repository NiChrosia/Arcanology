package nichrosia.arcanology.type.content.block.entity

import net.minecraft.SharedConstants
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
import nichrosia.arcanology.registry.category.ArcanologyCategory.arcanology
import nichrosia.arcanology.type.content.block.ReactiveBlock
import nichrosia.arcanology.type.content.status.effect.instance.ElementalWrathStatusEffectInstance
import nichrosia.registry.Registrar

open class ReactiveBlockEntity(pos: BlockPos, state: BlockState, override val block: ReactiveBlock) : BlockEntity(Registrar.arcanology.blockEntity.reactiveBlock, pos, state),
    BlockEntityWithBlock<ReactiveBlock> {
    companion object {
        fun onBreak(pos: BlockPos, playerEntity: PlayerEntity) {
            playerEntity.removeStatusEffect(Registrar.arcanology.statusEffect.elementalWrath)
            playerEntity.addStatusEffect(ElementalWrathStatusEffectInstance(SharedConstants.TICKS_PER_SECOND * 30, pos))
        }
    }
}