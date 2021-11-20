@file:Suppress("UnstableApiUsage")

package arcanology.type.common.world.block.entity

import arcanology.type.common.world.block.FluidMachineBlock
import arcanology.type.common.world.data.fluid.FluidTier
import arcanology.type.common.world.data.storage.fluid.SimpleFluidStorage
import arcanology.type.common.world.recipe.SimpleRecipe
import arcanology.type.common.world.recipe.fluid.FluidRecipe
import arcanology.type.graphics.ui.gui.property.KPropertyDelegate
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant
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

abstract class FluidMachineBlockEntity<B : FluidMachineBlock<B, S, T>, S : ScreenHandler, R : FluidRecipe<T, R>, T : FluidMachineBlockEntity<B, S, R, T>>(
    type: BlockEntityType<T>,
    pos: BlockPos,
    state: BlockState,
    inputSlots: Array<Int>,
    outputSlots: Array<Int>,
    block: B,
    handlerConstructor: (Int, PlayerInventory, ScreenHandlerContext) -> S,
    recipeType: SimpleRecipe.Type<R>,
    title: Text = TranslatableText(block.translationKey),
    inputSides: Array<Direction> = Direction.values(),
    outputSides: Array<Direction> = Direction.values()
) : MachineBlockEntity<B, S, R, T>(
    type,
    pos,
    state,
    inputSlots,
    outputSlots,
    block,
    handlerConstructor,
    title,
    recipeType,
    inputSides,
    outputSides
) {
    open val fluidStorage = BlockEntityFluidStorage(block.fluidTier)

    open val fluidID: Int
        get() = Registry.FLUID.indexOf(fluidStorage.variant.fluid)

    override val delegate = KPropertyDelegate(
        recipeProgressor::progress,
        recipeProgressor::maxProgress,
        energyStorage::energyAmount,
        energyStorage::energyCapacity,
        fluidStorage::fluidAmount,
        fluidStorage::fluidCapacity,
        this::fluidID,
    )

    open inner class BlockEntityFluidStorage(tier: FluidTier, initial: Long = 0L) : SimpleFluidStorage(FluidVariant.blank(), tier.maxInsert, tier.maxExtract, tier.capacity, initial) {
        override var fluidAmount by nbtFieldOf(initial)

        init {
            nbtObjects.add(this)
        }

        override fun onFinalCommit() {
            markDirty()
        }
    }
}