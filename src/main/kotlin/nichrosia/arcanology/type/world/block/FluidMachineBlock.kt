@file:Suppress("UnstableApiUsage", "DEPRECATION")

package nichrosia.arcanology.type.world.block

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.minecraft.block.BlockState
import net.minecraft.screen.ScreenHandler
import net.minecraft.util.math.BlockPos
import nichrosia.arcanology.type.world.block.entity.FluidMachineBlockEntity
import nichrosia.arcanology.type.world.block.settings.MachineBlockSettings

abstract class FluidMachineBlock<B : FluidMachineBlock<B, S, E>, S : ScreenHandler, E : FluidMachineBlockEntity<B, S, *, E>>(
    settings: MachineBlockSettings,
    entityConstructor: (BlockPos, BlockState, B) -> E
) : MachineBlock<B, S, E>(settings, entityConstructor) {
    init {
        FluidStorage.SIDED.registerForBlocks({ _, _, _, blockEntity, _ ->
            (blockEntity as? FluidMachineBlockEntity<*, *, *, *>)?.fluidStorage
        }, this)
    }
}