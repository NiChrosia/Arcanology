package arcanology.type.common.world.block.entity

import arcanology.type.common.ui.descriptor.SmelterGuiDescriptor
import arcanology.type.common.world.block.SmelterBlock
import arcanology.type.common.world.recipe.SmelterRecipe
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos

open class SmelterBlockEntity(
    pos: BlockPos, state: BlockState, block: SmelterBlock
) : FluidMachineBlockEntity<SmelterBlock, SmelterGuiDescriptor, SmelterRecipe, SmelterBlockEntity>(
    block.type, pos, state, arrayOf(0), arrayOf(), block, ::SmelterGuiDescriptor, SmelterRecipe.Type
)