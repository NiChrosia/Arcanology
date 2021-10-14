@file:Suppress("DEPRECATION")

package nichrosia.arcanology.type.block.entity

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
import net.minecraft.nbt.NbtInt
import net.minecraft.nbt.NbtLong
import net.minecraft.recipe.RecipeType
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
import net.minecraft.world.World
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.registry.impl.SoundRegistrar.Companion.length
import nichrosia.arcanology.type.block.MachineBlock
import nichrosia.arcanology.type.block.entity.inventory.AInventory
import nichrosia.arcanology.type.energy.EnergyTier
import nichrosia.arcanology.type.nbt.NBTEditor
import nichrosia.arcanology.type.property.MutableProperty
import nichrosia.arcanology.type.recipe.MachineRecipe
import nichrosia.arcanology.util.asNullable
import nichrosia.arcanology.util.setToList
import nichrosia.arcanology.util.toDefaultedList
import team.reborn.energy.api.base.SimpleEnergyStorage

/** A machine block entity with unified abstract methods for simplified usage. */
@Suppress("UnstableApiUsage", "DEPRECATION", "UNCHECKED_CAST")
abstract class MachineBlockEntity<B : MachineBlock<B, S, T>, S : ScreenHandler, R : MachineRecipe<T, *>, T : MachineBlockEntity<B, S, R, T>>(
    type: BlockEntityType<*>,
    pos: BlockPos,
    state: BlockState,
    inputSlots: Array<Int>,
    override val block: B,
    val outputSlots: Array<Int>,
    val guiDescriptionConstructor: (Int, PlayerInventory, ScreenHandlerContext) -> S,
    val inputDirections: Array<Direction> = Direction.values(),
    val screenName: Text = TranslatableText(block.translationKey),
) : BlockEntity(type, pos, state), NamedScreenHandlerFactory, AInventory, PropertyDelegateHolder, BlockEntityWithBlock<B> {
    override val inputSlots: IntArray = inputSlots.toIntArray()
    override val items: Array<ItemStack> = Array(inputSlots.size + outputSlots.size) { ItemStack.EMPTY }

    abstract val recipeType: RecipeType<R>
    
    open val energyStorage = BlockEntityEnergyStorage(block.tier)
    open val outputDirections = Direction.values()
    open val maxProgress = 1000
    open val maxSoundProgress = Registrar.sound.machinery.length

    open val nbtEditors = arrayOf(
        NBTEditor("total_energy", energyStorage::amount, { it.longValue() }, NbtLong::of),
        NBTEditor("progress", ::progress, { it.intValue() }, NbtInt::of)
    )

    open var progress = 0
    open var soundProgress = 0L

    override fun getPropertyDelegate() = KPropertyDelegate(
        MutableProperty(::progress, { it }, { it }),
        MutableProperty(::maxProgress, { it }, { it }),
        MutableProperty(energyStorage::amount, { it.toInt() }, { it.toLong() }),
        MutableProperty(block.tier::storage, { it.toInt() }, { it.toLong() })
    )

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        nbtEditors.forEach { it.write(nbt) }

        Inventories.writeNbt(nbt, items.toDefaultedList())

        return super.writeNbt(nbt)
    }

    override fun readNbt(nbt: NbtCompound) {
        nbtEditors.forEach { it.read(nbt) }
        
        val stacks = items.toDefaultedList()
        Inventories.readNbt(nbt, stacks)
        items.setToList(stacks)

        super.readNbt(nbt)
    }

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity) = guiDescriptionConstructor(syncId, inv, ScreenHandlerContext.create(player.world, pos))
    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?) = inputDirections.contains(dir) && inputSlots.contains(slot)
    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction) = outputDirections.contains(dir) && outputSlots.contains(slot)
    override fun getDisplayName() = screenName

    /** The core ticking for this block entity. Should not be overridden unless core behavior needs to be changed. */
    open fun tick(world: World, pos: BlockPos, state: BlockState) {
        machineTick(world, pos, state)

        if (progress >= maxProgress) {
            onRecipeCompletion(world, pos, state)
        }

        if (soundProgress >= maxSoundProgress) {
            onSoundCompletion(world, pos)
        }
    }

    /** Various machine functions, such as consuming power when progressing, playing sound, etc. */
    open fun machineTick(world: World, pos: BlockPos, state: BlockState) {
        if (canProgress(world)) {
            progress += block.tier.baseProgressPerTick

            setState(world, pos, state, MachineBlock.active, true)

            if (soundProgress == 0L && !world.isClient) world.playSound(null, pos, Registrar.sound.machinery, SoundCategory.BLOCKS, 1f, 1f)

            soundProgress += 1

            val transaction = Transaction.openOuter()

            energyStorage.extract(block.tier.baseUsagePerTick, transaction)
            transaction.commit()

            markDirty()
        } else {
            if (!world.isClient) setState(world, pos, state, MachineBlock.active, false)
        }
    }

    /** Whether the machine can increase the progression value & consume power. */
    open fun canProgress(world: World): Boolean {
        return inputSlots.any { !items[it].isEmpty } &&
            energyStorage.amount >= block.tier.baseUsagePerTick &&
            world.recipeManager.getFirstMatch(recipeType, this as T, world).isPresent
    }

    /** What to do once the machine progress reaches maximum. */
    open fun onRecipeCompletion(world: World, pos: BlockPos, state: BlockState) {
        progress = 0

        world.recipeManager.getFirstMatch(recipeType, this as T, world).asNullable?.craft(this)
        if (items[inputSlots[0]].isEmpty) setState(world, pos, state, MachineBlock.active, false)

        markDirty()
    }

    /** What to do when finished with playing sound, used for looping by default. */
    open fun onSoundCompletion(world: World, pos: BlockPos) {
        soundProgress = 0L

        if (!world.isClient) world.playSound(null, pos, Registrar.sound.machinery, SoundCategory.BLOCKS, 1f, 1f)

        markDirty()
    }

    /** Utility function for setting [BlockState]s for sprite changes. */
    open fun <T : Comparable<T>> setState(world: World, pos: BlockPos, currentState: BlockState, property: Property<T>, value: T): BlockState {
        val state = currentState.with(property, value)

        world.setBlockState(pos, state)

        return state
    }

    open inner class KPropertyDelegate(vararg val properties: MutableProperty<*, Int>) : PropertyDelegate {
        override fun get(index: Int): Int {
            return properties[index]()
        }

        override fun set(index: Int, value: Int) {
            properties[index](value)
        }

        override fun size(): Int {
            return properties.size
        }
    }

    open inner class BlockEntityEnergyStorage(tier: EnergyTier) : SimpleEnergyStorage(tier.storage, tier.maxInput, tier.maxOutput) {
        override fun onFinalCommit() {
            markDirty()
        }
    }
}