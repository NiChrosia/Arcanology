package arcanology.common.type.api.world.block

import arcanology.common.type.api.world.entity.block.MachineBlockEntity
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class ScreenMachineBlock<E>(settings: Settings) : MachineBlock<E>(settings) where E : MachineBlockEntity<E>, E : NamedScreenHandlerFactory {
    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult {
        val factory = state.createScreenHandlerFactory(world, pos)

        player.openHandledScreen(factory)

        return ActionResult.SUCCESS
    }
}