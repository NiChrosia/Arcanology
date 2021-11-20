package arcanology.type.common.world.block.machine.progressor

import arcanology.type.api.common.world.block.entity.EnergyBlockEntity
import arcanology.type.api.common.world.block.inventory.SimpleInventory
import arcanology.type.common.world.block.MachineBlock
import arcanology.type.common.world.data.nbt.NbtContainer
import arcanology.type.common.world.recipe.SimpleRecipe
import arcanology.util.world.blocks.setState
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

open class RecipeProgressor<E, R : SimpleRecipe<E, R>>(
    val recipe: SimpleRecipe.Type<R>,
    val reference: E,
    override val maxProgress: Long,
) : Progressor where E : EnergyBlockEntity, E : BlockEntity, E : NbtContainer, E : SimpleInventory {
    override var incrementAmount by reference.nbtFieldOf(1L, "recipeIncrementAmount")
    override var progress by reference.nbtFieldOf(0L, "recipeProgress")

    override fun valid(world: World, pos: BlockPos, state: BlockState): Boolean {
        val hasInput = reference.run {
            inputSlots.all { !items[it].isEmpty }
        }

        val recipeValid = reference.run {
            world.recipeManager.getFirstMatch(recipe, this, world).isPresent
        }

        return super.valid(world, pos, state) && hasInput && recipeValid
    }

    override fun consume(world: World, pos: BlockPos, state: BlockState) {
        super.consume(world, pos, state)

        reference.apply {
            setState(world, pos, state, MachineBlock.active, true)

            markDirty()
        }
    }

    override fun idle(world: World, pos: BlockPos, state: BlockState) {
        super.idle(world, pos, state)

        setState(world, pos, state, MachineBlock.active, false)
    }

    override fun complete(world: World, pos: BlockPos, state: BlockState) {
        super.complete(world, pos, state)

        reference.apply {
            world.recipeManager.getFirstMatch(recipe, this, world).ifPresent { it.craft(this) }
            if (items[inputSlots[0]].isEmpty) setState(world, pos, state, MachineBlock.active, false)

            markDirty()
        }
    }
}