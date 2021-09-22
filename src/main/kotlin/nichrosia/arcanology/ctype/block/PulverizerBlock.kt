package nichrosia.arcanology.ctype.block

import dev.technici4n.fasttransferlib.api.energy.EnergyApi
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.InventoryProvider
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.SidedInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.ItemScatterer
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import nichrosia.arcanology.type.energy.EnergyTier
import nichrosia.arcanology.ctype.block.entity.PulverizerBlockEntity
import nichrosia.arcanology.registry.Registrar

@Suppress("deprecation", "LeakingThis")
open class PulverizerBlock(settings: Settings, val tier: EnergyTier) : BlockWithEntity(settings), InventoryProvider {
    init {
        EnergyApi.SIDED.registerForBlocks({ _, _, _, blockEntity, _ ->
            blockEntity as? PulverizerBlockEntity
        }, this)
    }

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return PulverizerBlockEntity(pos, state, this)
    }

    override fun getRenderType(state: BlockState): BlockRenderType {
        return BlockRenderType.MODEL
    }

    override fun <T : BlockEntity> getTicker(
        world: World,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return checkType(type, Registrar.blockEntity.pulverizer) { world1, pos, state1, be ->
            PulverizerBlockEntity.tick(world1, pos, state1, be as PulverizerBlockEntity)
        }
    }

    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult {
        player.openHandledScreen(state.createScreenHandlerFactory(world, pos))

        return ActionResult.SUCCESS
    }

    override fun onStateReplaced(
        state: BlockState,
        world: World,
        pos: BlockPos,
        newState: BlockState,
        moved: Boolean
    ) {
        if (state.block !== newState.block) {
            val blockEntity = world.getBlockEntity(pos)

            if (blockEntity is PulverizerBlockEntity) {
                ItemScatterer.spawn(world, pos, blockEntity)

                world.updateComparators(pos, this)
            }

            super.onStateReplaced(state, world, pos, newState, moved)
        }
    }

    override fun hasComparatorOutput(state: BlockState?): Boolean {
        return true
    }

    override fun getComparatorOutput(state: BlockState, world: World, pos: BlockPos): Int {
        return ScreenHandler.calculateComparatorOutput(world.getBlockEntity(pos))
    }

    override fun getInventory(state: BlockState, world: WorldAccess, pos: BlockPos): SidedInventory {
        return (world.getBlockEntity(pos) as PulverizerBlockEntity?)!!
    }
}