@file:Suppress("DEPRECATION")

package arcanology.type.common.world.block.entity

import arcanology.Arcanology.arcanology
import arcanology.type.api.common.world.block.entity.BlockEntityWithBlock
import arcanology.type.api.common.world.block.entity.EnergyBlockEntity
import arcanology.type.api.common.world.block.entity.ScreenBlockEntity
import arcanology.type.api.common.world.block.inventory.SimpleInventory
import arcanology.type.common.world.block.MachineBlock
import arcanology.type.common.world.block.machine.Machine
import arcanology.type.common.world.block.machine.progressor.EnergyRecipeProgressor
import arcanology.type.common.world.block.machine.progressor.SoundProgressor
import arcanology.type.common.world.data.nbt.NbtContainer
import arcanology.type.common.world.recipe.SimpleRecipe
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import nichrosia.common.record.registrar.Registrar

@Suppress("UnstableApiUsage", "DEPRECATION", "UNCHECKED_CAST")
abstract class MachineBlockEntity<B : MachineBlock<B, S, T>, S : ScreenHandler, R : SimpleRecipe<T, R>, T : MachineBlockEntity<B, S, R, T>>(
    type: BlockEntityType<T>,
    pos: BlockPos,
    state: BlockState,
    override val inputSlots: Array<Int>,
    override val outputSlots: Array<Int>,
    override val block: B,
    override val handlerConstructor: (Int, PlayerInventory, ScreenHandlerContext) -> S,
    override val title: Text = TranslatableText(block.translationKey),
    recipeType: SimpleRecipe.Type<R>,
    override val inputSides: Array<Direction> = Direction.values(),
    override val outputSides: Array<Direction> = Direction.values(),
) : BlockEntity(type, pos, state), NbtContainer, SimpleInventory, BlockEntityWithBlock<B>, EnergyBlockEntity, ScreenBlockEntity<S>, Machine {
    override val nbtObjects = mutableListOf(inventoryNbtObject)
    override val items = emptyInventoryOf(slotSize)
    override val energyStorage = energyStorageOf(block.energyTier)
    override val delegate = energyPropertyDelegateOf(block.energyTier)

    override val recipeProgressor = EnergyRecipeProgressor(recipeType, block.energyTier, this as T)
    open val soundProgressor = SoundProgressor(recipeProgressor, Registrar.arcanology.sound.machinery)

    override val progressors = arrayOf(recipeProgressor, soundProgressor)

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        return super.writeNbt(writeNbtObjects(nbt))
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(readNbtObjects(nbt))
    }

    open fun tick(world: World, pos: BlockPos, state: BlockState) {
        progress(world, pos, state)
    }
}