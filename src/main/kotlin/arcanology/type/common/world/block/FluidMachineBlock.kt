@file:Suppress("UnstableApiUsage", "DEPRECATION")

package arcanology.type.common.world.block

import arcanology.type.common.world.block.entity.FluidMachineBlockEntity
import arcanology.type.common.world.block.settings.FluidMachineBlockSettings
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.minecraft.block.BlockState
import net.minecraft.screen.ScreenHandler
import net.minecraft.util.math.BlockPos

abstract class FluidMachineBlock<B : FluidMachineBlock<B, S, E>, S : ScreenHandler, E : FluidMachineBlockEntity<B, S, *, E>>(
    settings: FluidMachineBlockSettings,
    entityConstructor: (BlockPos, BlockState, B) -> E
) : MachineBlock<B, S, E>(settings, entityConstructor) {
    open val fluidTier = settings.fluidTier

    init {
        FluidStorage.SIDED.registerForBlocks({ _, _, _, blockEntity, _ ->
            (blockEntity as? FluidMachineBlockEntity<*, *, *, *>)?.fluidStorage
        }, this)
    }
}