@file:Suppress("DEPRECATION")

package nichrosia.arcanology.type.content.block.entity

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.World
import nichrosia.arcanology.Arcanology.arcanology
import nichrosia.arcanology.type.content.api.block.entity.BlockEntityWithBlock
import nichrosia.arcanology.type.content.api.block.entity.EnergyBlockEntity
import nichrosia.arcanology.type.content.api.block.entity.ScreenBlockEntity
import nichrosia.arcanology.type.content.api.block.entity.SoundBlockEntity
import nichrosia.arcanology.type.content.api.block.entity.inventory.BasicInventory
import nichrosia.arcanology.type.content.block.MachineBlock
import nichrosia.arcanology.type.content.block.entity.storage.BlockEntityEnergyStorage
import nichrosia.arcanology.type.content.block.entity.storage.BlockEntityItems
import nichrosia.arcanology.type.content.gui.property.KPropertyDelegate
import nichrosia.arcanology.type.content.recipe.SimpleRecipe
import nichrosia.arcanology.type.nbt.NbtContainer
import nichrosia.arcanology.type.nbt.NbtObject
import nichrosia.arcanology.util.*
import nichrosia.common.record.registrar.Registrar

/** A machine block entity with unified abstract methods for simplified usage. */
@Suppress("UnstableApiUsage", "DEPRECATION", "UNCHECKED_CAST")
abstract class MachineBlockEntity<B : MachineBlock<B, S, T>, S : ScreenHandler, R : SimpleRecipe<T, R>, T : MachineBlockEntity<B, S, R, T>>(
    type: BlockEntityType<T>,
    pos: BlockPos,
    state: BlockState,
    override val inputSlots: Array<Int>,
    override val block: B,
    override val handlerConstructor: (Int, PlayerInventory, ScreenHandlerContext) -> S,
    override val title: Text = TranslatableText(block.translationKey),
    val outputSlots: Array<Int>,
    val recipeType: SimpleRecipe.Type<R>,
    val inputDirections: Array<Direction> = Direction.values(),
) : BlockEntity(type, pos, state),
    NbtContainer,
    BasicInventory,
    BlockEntityWithBlock<B>,
    EnergyBlockEntity,
    ScreenBlockEntity<S>,
    SoundBlockEntity
{
    override val nbtObjects = mutableListOf<NbtObject>()
    override val blockPos = pos
    override val items = BlockEntityItems(this, ItemStack.EMPTY.repeat(inputSlots.size + outputSlots.size, ItemStack::copy).toMutableList())
    override val energyStorage = BlockEntityEnergyStorage(this, block.tier)
    override val delegate = KPropertyDelegate(this::progress, block.tier::maxProgress, energyStorage::energyAmount, energyStorage::energyCapacity)
    override val sound = Registrar.arcanology.sound.machinery

    open val outputDirections = Direction.values()

    override var soundProgress = 0L

    open var progress by nbtFieldOf(0L)

    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?): Boolean {
        return inputDirections.contains(dir) && inputSlots.contains(slot)
    }

    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction): Boolean {
        return outputDirections.contains(dir) && outputSlots.contains(slot)
    }

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        return super.writeNbt(writeNbtObjects(nbt))
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(readNbtObjects(nbt))
    }

    open fun tick(world: World, pos: BlockPos, state: BlockState) {
        if (world.isServer) {
            if (canProgress(world)) {
                progress += block.tier.progressionSpeed

                setState(world, pos, state, MachineBlock.active, true)
                changeEnergyBy(block.tier.consumptionSpeed)
                progressSound(world, pos)

                markDirty()
            } else {
                setState(world, pos, state, MachineBlock.active, false)
            }

            if (progress >= block.tier.maxProgress) {
                onRecipeCompletion(world, pos, state)
                markDirty()
            }

            if (soundProgress >= sound.length) {
                onSoundCompletion(world, pos)
                markDirty()
            }
        }
    }

    open fun canProgress(world: World): Boolean {
        return inputSlots.any { !items[it].isEmpty } && energyStorage.amount >= block.tier.consumptionSpeed && world.recipeManager.getFirstMatch(recipeType, this as T, world).isPresent
    }

    open fun onRecipeCompletion(world: World, pos: BlockPos, state: BlockState) {
        progress = 0L

        world.recipeManager.getFirstMatch(recipeType, this as T, world).ifPresent { it.craft(this) }
        if (items[inputSlots[0]].isEmpty) setState(world, pos, state, MachineBlock.active, false)

        markDirty()
    }
}