package nichrosia.arcanology.ctype.block.entity

import net.minecraft.SharedConstants
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
import nichrosia.arcanology.ctype.block.ReactiveBlock
import nichrosia.arcanology.ctype.status.effect.instance.ElementalWrathStatusEffectInstance
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.type.block.entity.BlockEntityWithBlock

open class ReactiveBlockEntity(pos: BlockPos, state: BlockState, override val block: ReactiveBlock) : BlockEntity(
    Registrar.blockEntity.reactiveBlock, pos, state), BlockEntityWithBlock<ReactiveBlock> {
    companion object {
        fun onBreak(pos: BlockPos, playerEntity: PlayerEntity) {
            playerEntity.removeStatusEffect(Registrar.statusEffect.elementalWrath)
            playerEntity.addStatusEffect(ElementalWrathStatusEffectInstance(SharedConstants.TICKS_PER_SECOND * 30, pos))
        }
    }
}