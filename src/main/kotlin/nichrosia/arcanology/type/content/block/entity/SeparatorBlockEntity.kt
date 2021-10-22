package nichrosia.arcanology.type.content.block.entity

import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.type.block.entity.MachineBlockEntity
import nichrosia.arcanology.type.content.block.SeparatorBlock
import nichrosia.arcanology.type.content.gui.description.SeparatorGUIDescription
import nichrosia.arcanology.type.content.recipe.SeparatorRecipe
import nichrosia.arcanology.type.recipe.MachineRecipe

open class SeparatorBlockEntity(
    pos: BlockPos, state: BlockState, block: SeparatorBlock
) : MachineBlockEntity<SeparatorBlock, SeparatorGUIDescription, SeparatorRecipe, SeparatorBlockEntity>(
    Registrar.blockEntity.separator, pos, state, arrayOf(0), block, arrayOf(1), ::SeparatorGUIDescription
) {
    override val recipeType: MachineRecipe.Type<SeparatorBlockEntity, SeparatorRecipe> = SeparatorRecipe.Type
}