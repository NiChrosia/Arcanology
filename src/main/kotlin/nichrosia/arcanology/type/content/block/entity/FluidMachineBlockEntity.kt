@file:Suppress("UnstableApiUsage")

package nichrosia.arcanology.type.content.block.entity

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.type.content.block.FluidMachineBlock
import nichrosia.arcanology.type.content.recipe.MachineRecipe
import nichrosia.arcanology.type.storage.fluid.SimpleFluidStorage

abstract class FluidMachineBlockEntity<B : FluidMachineBlock<B, S, T>, S : ScreenHandler, R : MachineRecipe<T, R>, T : FluidMachineBlockEntity<B, S, R, T>>(
    type: BlockEntityType<T>,
    pos: BlockPos,
    state: BlockState,
    inputSlots: Array<Int>,
    block: B,
    outputSlots: Array<Int>,
    guiDescriptionConstructor: (Int, PlayerInventory, ScreenHandlerContext) -> S,
    recipeType: MachineRecipe.Type<T, R>,
    inputDirections: Array<Direction> = Direction.values(),
    screenName: Text = TranslatableText(block.translationKey)
) : MachineBlockEntity<B, S, R, T>(type, pos, state, inputSlots, block, outputSlots, guiDescriptionConstructor, recipeType, inputDirections, screenName) {
    open val fluidStorage = BlockEntityFluidStorage()

    open val fluidID: Int
        get() = Registry.FLUID.indexOf(fluidStorage.variant.fluid)

    override val delegate: KPropertyDelegate = KPropertyDelegate(
        this::progress,
        block.tier::maxProgress,
        energyStorage::energyAmount,
        energyStorage::energyCapacity,
        fluidStorage::fluidAmount,
        fluidStorage::fluidCapacity,
        this::fluidID,
    )

    open inner class BlockEntityFluidStorage(initial: Long = 0L) : SimpleFluidStorage(initial = initial) {
        override var fluidAmount by nbtFieldOf(initial)

        init {
            nbtObjects.add(this)
        }

        override fun onFinalCommit() {
            markDirty()
        }
    }
}