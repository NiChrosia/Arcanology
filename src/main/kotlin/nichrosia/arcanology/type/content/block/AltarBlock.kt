package nichrosia.arcanology.type.content.block

import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import nichrosia.arcanology.type.content.block.entity.AltarBlockEntity
import nichrosia.arcanology.registry.Registrar

open class AltarBlock(settings: Settings) : BlockWithEntity(settings) {
    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return AltarBlockEntity(pos, state, this)
    }

    override fun getRenderType(state: BlockState): BlockRenderType {
        return BlockRenderType.MODEL
    }

    override fun <T : BlockEntity> getTicker(
        world: World,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return checkType(type, Registrar.blockEntity.altar) { world1: World, pos: BlockPos, state1: BlockState, be: BlockEntity ->
            AltarBlockEntity.tick(world1, pos, state1, be as AltarBlockEntity)
        }
    }

    override fun onBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        world.playSound(
            player,
            pos,
            SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK,
            SoundCategory.BLOCKS,
            1f,
            1f
        )

        super.onBreak(world, pos, state, player)
    }
}