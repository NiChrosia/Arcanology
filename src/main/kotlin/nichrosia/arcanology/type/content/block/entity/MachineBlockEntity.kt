@file:Suppress("DEPRECATION")

package nichrosia.arcanology.type.content.block.entity

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
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
import net.minecraft.world.World
import nichrosia.arcanology.Arcanology.arcanology
import nichrosia.arcanology.type.content.block.MachineBlock
import nichrosia.arcanology.type.content.block.entity.inventory.BasicInventory
import nichrosia.arcanology.type.content.recipe.SimpleRecipe
import nichrosia.arcanology.type.energy.EnergyTier
import nichrosia.arcanology.type.nbt.NbtContainer
import nichrosia.arcanology.type.nbt.NbtObject
import nichrosia.arcanology.type.storage.energy.ExtensibleEnergyStorage
import nichrosia.arcanology.util.*
import nichrosia.common.record.registrar.Registrar
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty0

/** A machine block entity with unified abstract methods for simplified usage. */
@Suppress("UnstableApiUsage", "DEPRECATION", "UNCHECKED_CAST")
abstract class MachineBlockEntity<B : MachineBlock<B, S, T>, S : ScreenHandler, R : SimpleRecipe<T, R>, T : MachineBlockEntity<B, S, R, T>>(
    type: BlockEntityType<T>,
    pos: BlockPos,
    state: BlockState,
    override val inputSlots: Array<Int>,
    override val block: B,
    val outputSlots: Array<Int>,
    val guiDescriptionConstructor: (Int, PlayerInventory, ScreenHandlerContext) -> S,
    val recipeType: SimpleRecipe.Type<R>,
    val inputDirections: Array<Direction> = Direction.values(),
    val screenName: Text = TranslatableText(block.translationKey),
) : BlockEntity(type, pos, state), NbtContainer, NamedScreenHandlerFactory, BasicInventory, PropertyDelegateHolder, BlockEntityWithBlock<B> {
    override val nbtObjects = mutableListOf<NbtObject>()
    override val items = BlockEntityItems()

    open val energyStorage = BlockEntityEnergyStorage(block.tier)

    open val sound = Registrar.arcanology.sound.machinery

    open val outputDirections = Direction.values()

    open val delegate = KPropertyDelegate(
        this::progress,
        block.tier::maxProgress,
        energyStorage::energyAmount,
        energyStorage::energyCapacity
    )

    open var progress by nbtFieldOf(0L)
    open var soundProgress = 0L

    override fun getDisplayName() = screenName
    override fun getPropertyDelegate() = delegate

    override fun canInsert(slot: Int, stack: ItemStack, dir: Direction?): Boolean {
        return inputDirections.contains(dir) && inputSlots.contains(slot)
    }

    override fun canExtract(slot: Int, stack: ItemStack, dir: Direction): Boolean {
        return outputDirections.contains(dir) && outputSlots.contains(slot)
    }

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity): ScreenHandler {
        return guiDescriptionConstructor(syncId, inv, ScreenHandlerContext.create(player.world, pos))
    }

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        nbtObjects.forEach { it.writeNbt(nbt) }

        return super.writeNbt(nbt)
    }

    override fun readNbt(nbt: NbtCompound) {
        nbtObjects.forEach { it.readNbt(nbt) }

        super.readNbt(nbt)
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

            if (progress >= block.tier.maxProgress) onRecipeCompletion(world, pos, state)
            if (soundProgress >= sound.length) onSoundCompletion(world, pos)
        }
    }

    open fun canProgress(world: World): Boolean {
        return inputSlots.any { !items[it].isEmpty } && energyStorage.amount >= block.tier.consumptionSpeed && world.recipeManager.getFirstMatch(recipeType, this as T, world).isPresent
    }

    open fun progressSound(world: World, pos: BlockPos) {
        if (soundProgress == 0L) {
            world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1f, 1f)
        }

        soundProgress++
    }

    open fun onRecipeCompletion(world: World, pos: BlockPos, state: BlockState) {
        progress = 0L

        world.recipeManager.getFirstMatch(recipeType, this as T, world).ifPresent { it.craft(this) }
        if (items[inputSlots[0]].isEmpty) setState(world, pos, state, MachineBlock.active, false)

        markDirty()
    }

    open fun onSoundCompletion(world: World, pos: BlockPos) {
        soundProgress = 0L

        world.playSound(null, pos, sound, SoundCategory.BLOCKS, 1f, 1f)

        markDirty()
    }

    open fun <T : Comparable<T>> setState(world: World, pos: BlockPos, currentState: BlockState, property: Property<T>, value: T): BlockState {
        return currentState.with(property, value).also {
            world.setBlockState(pos, it)
        }
    }

    open fun changeEnergyBy(amount: Long) {
        val transaction = Transaction.openOuter()

        when {
            amount > 0L -> energyStorage.insert(amount, transaction)
            amount < 0L -> energyStorage.extract(amount, transaction)
            else -> throw IllegalArgumentException("Cannot change energy by value of zero.")
        }

        transaction.commit()
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

    open inner class BlockEntityItems : ModifiableList<ItemStack>(
        ItemStack.EMPTY.repeat(inputSlots.size + outputSlots.size, ItemStack::copy)
    ), NbtObject {
        init {
            nbtObjects.add(this)
        }

        override fun writeNbt(nbt: NbtCompound): NbtCompound {
            return nbt.putInventory(this)
        }

        override fun readNbt(nbt: NbtCompound) {
            nbt.readToInventory(this)
        }
    }

    open inner class BlockEntityEnergyStorage(tier: EnergyTier, initial: Long = 0L) : ExtensibleEnergyStorage(tier.storage, tier.maxInputSpeed, tier.maxOutputSpeed) {
        override var energyAmount by nbtFieldOf(initial)

        override fun onFinalCommit() {
            markDirty()
        }
    }
}