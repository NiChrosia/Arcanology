package nichrosia.arcanology.type.content.block.entity

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import nichrosia.arcanology.registry.category.ArcanologyCategory.arcanology
import nichrosia.arcanology.type.content.block.SeparatorBlock
import nichrosia.arcanology.type.content.gui.description.SeparatorGuiDescription
import nichrosia.arcanology.type.content.recipe.SeparatorRecipe
import nichrosia.registry.Registrar

open class SeparatorBlockEntity(
    pos: BlockPos, state: BlockState, block: SeparatorBlock
) : MachineBlockEntity<SeparatorBlock, SeparatorGuiDescription, SeparatorRecipe, SeparatorBlockEntity>(
    Registrar.arcanology.blockEntity.separator, pos, state, intArrayOf(0), block, intArrayOf(1), ::SeparatorGuiDescription, SeparatorRecipe.Type
)