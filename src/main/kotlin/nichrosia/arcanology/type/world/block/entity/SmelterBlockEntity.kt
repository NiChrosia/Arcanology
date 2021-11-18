package nichrosia.arcanology.type.world.block.entity

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import nichrosia.arcanology.type.world.block.SmelterBlock
import nichrosia.arcanology.type.graphics.ui.gui.description.SmelterGuiDescription
import nichrosia.arcanology.type.world.recipe.SmelterRecipe

open class SmelterBlockEntity(
    pos: BlockPos, state: BlockState, block: SmelterBlock
) : FluidMachineBlockEntity<SmelterBlock, SmelterGuiDescription, SmelterRecipe, SmelterBlockEntity>(
    block.type, pos, state, arrayOf(0), block, arrayOf(), ::SmelterGuiDescription, SmelterRecipe.Type
)