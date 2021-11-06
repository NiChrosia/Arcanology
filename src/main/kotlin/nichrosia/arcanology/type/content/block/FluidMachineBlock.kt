@file:Suppress("UnstableApiUsage", "DEPRECATION")

package nichrosia.arcanology.type.content.block

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.screen.ScreenHandler
import net.minecraft.util.math.BlockPos
import nichrosia.arcanology.type.content.block.entity.FluidMachineBlockEntity
import nichrosia.arcanology.type.content.block.settings.ArcanologyBlockSettings
import nichrosia.arcanology.type.energy.EnergyTier

abstract class FluidMachineBlock<B : FluidMachineBlock<B, S, E>, S : ScreenHandler, E : FluidMachineBlockEntity<B, S, *, E>>(
    settings: ArcanologyBlockSettings,
    entityConstructor: (BlockPos, BlockState, B) -> E,
    type: () -> BlockEntityType<E>,
    tier: EnergyTier
) : MachineBlock<B, S, E>(settings, entityConstructor, type, tier) {
    init {
        FluidStorage.SIDED.registerForBlocks({ _, _, _, blockEntity, _ ->
            (blockEntity as? FluidMachineBlockEntity<*, *, *, *>)?.fluidStorage
        }, this)
    }
}