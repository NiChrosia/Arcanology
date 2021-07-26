package nichrosia.arcanology.type.blockentity

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import nichrosia.arcanology.content.BlockEntityTypes
import nichrosia.arcanology.content.StatusEffects
import nichrosia.arcanology.type.status.ElementalWrathStatusEffectInstance

open class ReactiveBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(BlockEntityTypes.reactiveBlockEntity, pos, state) {
    var owner: PlayerEntity? = null

    constructor(pos: BlockPos, state: BlockState, owner: PlayerEntity?) : this(pos, state) {
        this.owner = owner
    }

    companion object {
        fun onBreak(world: World, pos: BlockPos, playerEntity: PlayerEntity) {
            if ((world.getBlockEntity(pos) as ReactiveBlockEntity).owner?.uuidAsString == playerEntity.uuidAsString) return

            playerEntity.removeStatusEffect(StatusEffects.elementalWrath)
            playerEntity.addStatusEffect(ElementalWrathStatusEffectInstance(Int.MAX_VALUE, pos))
        }
    }
}