package nichrosia.arcanology.type.content.block

import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import nichrosia.arcanology.type.content.block.entity.ReactiveBlockEntity
import nichrosia.arcanology.type.id.block.AbstractBlock

open class ReactiveBlock(settings: Settings, override val ID: Identifier) : BlockWithEntity(settings), AbstractBlock {
    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return ReactiveBlockEntity(pos, state, this)
    }

    override fun getRenderType(state: BlockState): BlockRenderType {
        return BlockRenderType.MODEL
    }

    override fun onBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        ReactiveBlockEntity.onBreak(pos, player)

        super.onBreak(world, pos, state, player)
    }
}