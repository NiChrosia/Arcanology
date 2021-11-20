package arcanology.type.common.world.block.machine

import arcanology.type.common.world.block.machine.progressor.Progressor
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

interface Machine {
    val progressors: Array<out Progressor>
    val recipeProgressor: Progressor

    fun progress(world: World, pos: BlockPos, state: BlockState) {
        if (world.isClient) return

        progressors.forEach {
            it.progress(world, pos, state)
        }
    }

    fun compatible(upgrade: MachineUpgrade): Boolean {
        return true
    }
}