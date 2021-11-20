package arcanology.type.common.world.block.machine.progressor

import arcanology.type.api.common.world.block.entity.EnergyBlockEntity
import arcanology.type.api.common.world.block.inventory.SimpleInventory
import arcanology.type.common.world.data.energy.EnergyTier
import arcanology.type.common.world.data.nbt.NbtContainer
import arcanology.type.common.world.recipe.SimpleRecipe
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

open class EnergyRecipeProgressor<E, R : SimpleRecipe<E, R>>(recipe: SimpleRecipe.Type<R>, val tier: EnergyTier, reference: E) : RecipeProgressor<E, R>(recipe, reference, tier.maxProgress) where E : EnergyBlockEntity, E : BlockEntity, E : NbtContainer, E : SimpleInventory {
    override var incrementAmount = tier.progressionSpeed

    override fun valid(world: World, pos: BlockPos, state: BlockState): Boolean {
        val enoughEnergy = reference.run {
            energyStorage.energyAmount >= energyStorage.energyAmount - tier.consumptionSpeed
        }

        return super.valid(world, pos, state) && enoughEnergy
    }

    override fun consume(world: World, pos: BlockPos, state: BlockState) {
        super.consume(world, pos, state)

        reference.changeEnergyBy(tier.consumptionSpeed)
    }
}