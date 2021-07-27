package nichrosia.arcanology.type.block

import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import nichrosia.arcanology.type.blockentity.ReactiveBlockEntity

open class ReactiveBlock(settings: Settings) : BlockWithEntity(settings) {
    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return ReactiveBlockEntity(pos, state)
    }

    override fun getRenderType(state: BlockState): BlockRenderType {
        return BlockRenderType.MODEL
    }

    override fun onBreak(world: World, pos: BlockPos, state: BlockState, player: PlayerEntity) {
        ReactiveBlockEntity.onBreak(world, pos, player)

        super.onBreak(world, pos, state, player)
    }

    override fun onPlaced(
        world: World,
        pos: BlockPos,
        state: BlockState,
        placer: LivingEntity?,
        itemStack: ItemStack
    ) {
        (world.getBlockEntity(pos) as ReactiveBlockEntity?)?.let { blockEntity ->
            placer?.let { player ->
                blockEntity.owner = player as PlayerEntity
            }
        }

        super.onPlaced(world, pos, state, placer, itemStack)
    }
}