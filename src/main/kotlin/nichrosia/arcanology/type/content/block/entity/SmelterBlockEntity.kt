package nichrosia.arcanology.type.content.block.entity

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import nichrosia.arcanology.Arcanology.Category.arcanology
import nichrosia.arcanology.type.content.block.SmelterBlock
import nichrosia.arcanology.type.content.gui.description.SmelterGuiDescription
import nichrosia.arcanology.type.content.recipe.SmelterRecipe
import nichrosia.common.registry.type.Registrar

open class SmelterBlockEntity(
    pos: BlockPos, state: BlockState, block: SmelterBlock
) : FluidMachineBlockEntity<SmelterBlock, SmelterGuiDescription, SmelterRecipe, SmelterBlockEntity>(
    Registrar.arcanology.blockEntity.smelter, pos, state, arrayOf(0), block, arrayOf(1), ::SmelterGuiDescription, SmelterRecipe.Type
)