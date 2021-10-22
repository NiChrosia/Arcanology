package nichrosia.arcanology.type.content.block

import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.InventoryProvider
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.SidedInventory
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.type.content.block.entity.RuneInfuserBlockEntity
import nichrosia.arcanology.type.id.block.IdentifiedBlockWithEntity

open class RuneInfuserBlock(settings: Settings, ID: Identifier) : IdentifiedBlockWithEntity(settings, ID), InventoryProvider {
    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity? {
        return RuneInfuserBlockEntity(pos, state, this)
    }

    override fun getRenderType(state: BlockState): BlockRenderType {
        return BlockRenderType.MODEL
    }

    override fun getInventory(state: BlockState, world: WorldAccess, pos: BlockPos): SidedInventory {
        return (world.getBlockEntity(pos) as RuneInfuserBlockEntity?)!!
    }

    override fun <T : BlockEntity> getTicker(
        world: World,
        state: BlockState,
        type: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return checkType(type, Registrar.blockEntity.runeInfuser) { world1, pos, state1, be ->
            RuneInfuserBlockEntity.tick(world1, pos, state1, be as RuneInfuserBlockEntity)
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
}