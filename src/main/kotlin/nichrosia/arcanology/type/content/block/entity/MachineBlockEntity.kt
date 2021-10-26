@file:Suppress("DEPRECATION")

package nichrosia.arcanology.type.content.block.entity

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.sound.SoundCategory
import net.minecraft.state.property.Property
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import net.minecraft.world.World
import nichrosia.arcanology.registry.category.ArcanologyCategory.arcanology
import nichrosia.arcanology.type.content.block.MachineBlock
import nichrosia.arcanology.type.content.block.entity.inventory.AInventory
import nichrosia.arcanology.type.content.recipe.MachineRecipe
import nichrosia.arcanology.type.energy.EnergyTier
import nichrosia.arcanology.type.nbt.NbtContainer
import nichrosia.arcanology.type.nbt.NbtObject
import nichrosia.arcanology.type.storage.energy.ExtensibleEnergyStorage
import nichrosia.arcanology.type.storage.fluid.SimpleFluidStorage
import nichrosia.arcanology.util.asNullable
import nichrosia.arcanology.util.setToList
import nichrosia.arcanology.util.toDefaultedList
import nichrosia.registry.Registrar
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty0

/** A machine block entity with unified abstract methods for simplified usage. */
@Suppress("UnstableApiUsage", "DEPRECATION", "UNCHECKED_CAST")
abstract class MachineBlockEntity<B : MachineBlock<B, S, T>, S : ScreenHandler, R : MachineRecipe<T, R>, T : MachineBlockEntity<B, S, R, T>>(
    type: BlockEntityType<*>,
    pos: BlockPos,
    state: BlockState,
    override val inputSlots: IntArray,
    override val block: B,
    val outputSlots: IntArray,
    val guiDescriptionConstructor: (Int, PlayerInventory, ScreenHandlerContext) -> S,
    val recipeType: MachineRecipe.Type<T, R>,
    val inputDirections: Array<Direction> = Direction.values(),
    val screenName: Text = TranslatableText(block.translationKey),
) : BlockEntity(type, pos, state), NamedScreenHandlerFactory, AInventory, PropertyDelegateHolder, BlockEntityWithBlock<B>, NbtContainer {
    override val items: Array<ItemStack> = Array(inputSlots.size + outputSlots.size) { ItemStack.EMPTY }
    override val nbtObjects: MutableList<NbtObject> = mutableListOf()

    open val energyStorage = BlockEntityEnergyStorage(block.tier)
    open val fluidStorage = BlockEntityFluidStorage().also(nbtObjects::add)

    open val fluidID: Int
        get() = Registry.FLUID.indexOf(fluidStorage.variant.fluid)

    open val sound = Registrar.arcanology.sound.machinery

    open val outputDirections = Direction.values()

    open val delegate = KPropertyDelegate(
        this::progress,
        block.tier::maxProgress,
        energyStorage::energyAmount,
        energyStorage::energyCapacity,
        fluidStorage::fluidAmount,
        fluidStorage::fluidCapacity,
        this::fluidID,
    )

    open var progress by nbtFieldOf(0.0)
    open var soundProgress = 0L

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        Inventories.writeNbt(nbt, items.toDefaultedList())

        nbtObjects.forEach { it.writeNbt(nbt) }

        return super.writeNbt(nbt)
    }

    override fun readNbt(nbt: NbtCompound) {
        val stacks = items.toDefaultedList()
        Inventories.readNbt(nbt, stacks)
        items.setToList(stacks)

        nbtObjects.forEach { it.readNbt(nbt) }

        super.readNbt(nbt)
    }

    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?) = inputDirections.contains(dir) && inputSlots.contains(slot)
    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction) = outputDirections.contains(dir) && outputSlots.contains(slot)
    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity) = guiDescriptionConstructor(syncId, inv, ScreenHandlerContext.create(player.world, pos))
    override fun getDisplayName() = screenName
    override fun getPropertyDelegate() = delegate

    /** The core ticking for this block entity. Should not be overridden unless core behavior needs to be changed. */
    open fun tick(world: World, pos: BlockPos, state: BlockState) {
        machineTick(world, pos, state)

        if (progress >= block.tier.maxProgress) onRecipeCompletion(world, pos, state)
        if (soundProgress >= sound.length) onSoundCompletion(world, pos)
    }

    /** Various machine functions, such as consuming power when progressing, playing sound, etc. */
    // TODO find a more intuitive name & split into more definitive functions
    open fun machineTick(world: World, pos: BlockPos, state: BlockState) {
        if (canProgress(world)) {
            progress += block.tier.progressionSpeed

            setState(world, pos, state, MachineBlock.active, true)

            if (soundProgress == 0L && !world.isClient) world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1f, 1f)

            soundProgress += 1

            val transaction = Transaction.openOuter()

            energyStorage.extract(block.tier.consumptionSpeed, transaction)
            transaction.commit()

            markDirty()
        } else {
            if (!world.isClient) setState(world, pos, state, MachineBlock.active, false)
        }
    }

    /** Whether the machine can increase the progression value & consume power. */
    open fun canProgress(world: World): Boolean {
        return inputSlots.any { !items[it].isEmpty } &&
            energyStorage.amount >= block.tier.consumptionSpeed &&
            world.recipeManager.getFirstMatch(recipeType, this as T, world).isPresent
    }

    /** What to do once the machine progress reaches maximum. */
    open fun onRecipeCompletion(world: World, pos: BlockPos, state: BlockState) {
        progress = 0.0

        world.recipeManager.getFirstMatch(recipeType, this as T, world).asNullable?.craft(this)
        if (items[inputSlots[0]].isEmpty) setState(world, pos, state, MachineBlock.active, false)

        markDirty()
    }

    /** What to do when finished with playing sound, used for looping by default. */
    open fun onSoundCompletion(world: World, pos: BlockPos) {
        soundProgress = 0L

        if (!world.isClient) world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1f, 1f)

        markDirty()
    }

    /** Utility function for setting [BlockState]s for sprite changes. */
    open fun <T : Comparable<T>> setState(world: World, pos: BlockPos, currentState: BlockState, property: Property<T>, value: T): BlockState {
        return currentState.with(property, value).also {
            world.setBlockState(pos, it)
        }
    }

    open inner class KPropertyDelegate(vararg properties: KProperty0<Number>) : PropertyDelegate {
        open val properties = mutableListOf(*properties)

        override fun get(index: Int): Int {
            return properties[index]().toInt()
        }

        override fun set(index: Int, value: Int) {
            properties[index].also {
                if (it is KMutableProperty0<Number>) it.set(value)
            }
        }

        override fun size(): Int {
            return properties.size
        }
    }

    open inner class BlockEntityEnergyStorage(tier: EnergyTier, initial: Long = 0L) : ExtensibleEnergyStorage(tier.storage, tier.maxInputSpeed, tier.maxOutputSpeed) {
        override var energyAmount by nbtFieldOf(initial)

        override fun onFinalCommit() {
            markDirty()
        }
    }

    open inner class BlockEntityFluidStorage(initial: Long = 0L) : SimpleFluidStorage(initial = initial) {
        override var fluidAmount by nbtFieldOf(initial)

        override fun onFinalCommit() {
            markDirty()
        }
    }
}