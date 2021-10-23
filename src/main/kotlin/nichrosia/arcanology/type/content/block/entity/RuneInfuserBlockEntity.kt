package nichrosia.arcanology.type.content.block.entity

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable
import net.minecraft.block.BlockState
import net.minecraft.block.InventoryProvider
import net.minecraft.block.entity.LootableContainerBlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.text.LiteralText
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.WorldAccess
import nichrosia.arcanology.registry.category.ArcanologyCategory.arcanology
import nichrosia.arcanology.type.content.block.entity.inventory.AInventory
import nichrosia.arcanology.type.content.block.RuneInfuserBlock
import nichrosia.arcanology.type.content.gui.description.RuneInfuserGUIDescription
import nichrosia.arcanology.type.content.recipe.RuneRecipe
import nichrosia.arcanology.type.delegates.block.entity.inventory.ItemSlot
import nichrosia.arcanology.type.content.rune.RuneType
import nichrosia.arcanology.util.*
import nichrosia.registry.Registrar
import kotlin.reflect.KMutableProperty0

@Suppress("MemberVisibilityCanBePrivate", "NestedLambdaShadowedImplicitParameter")
open class RuneInfuserBlockEntity(pos: BlockPos, state: BlockState, override val block: RuneInfuserBlock) :
    LootableContainerBlockEntity(Registrar.arcanology.blockEntity.runeInfuser, pos, state),
    NamedScreenHandlerFactory,
    PropertyDelegateHolder,
    BlockEntityClientSerializable,
    AInventory,
    InventoryProvider,
    BlockEntityWithBlock<RuneInfuserBlock>, Scheduler {
    override val inputSlots = (1..6).map { it }.toIntArray()
    override var items: Array<ItemStack> = Array(7) { ItemStack.EMPTY }
    override var schedule: MutableList<Scheduler.Task> = mutableListOf()

    private val delegate = object : PropertyDelegate {
        override fun get(index: Int): Int {
            return when(index) {
                0 -> progress
                1 -> maxProgress
                2 -> runeID
                3 -> recipeValid
                else -> -1
            }
        }

        override fun set(index: Int, value: Int) {
            when(index) {
                0 -> progress = value
                2 -> runeID = value
                3 -> recipeValid = value
            }
        }

        override fun size(): Int {
            return 4
        }
    }

    open var progress = 0
    open val maxProgress = 100

    open var runeID = -1
    open var recipeValid = 0

    var resultSlot by ItemSlot<RuneInfuserBlockEntity>(0)
    var lightSlot by ItemSlot<RuneInfuserBlockEntity>(1)
    var voidSlot by ItemSlot<RuneInfuserBlockEntity>(2)
    var fireSlot by ItemSlot<RuneInfuserBlockEntity>(3)
    var waterSlot by ItemSlot<RuneInfuserBlockEntity>(4)
    var earthSlot by ItemSlot<RuneInfuserBlockEntity>(5)
    var airSlot by ItemSlot<RuneInfuserBlockEntity>(6)

    val inputSlotStacks: Array<KMutableProperty0<ItemStack>>
        get() = arrayOf(::lightSlot, ::voidSlot, ::fireSlot, ::waterSlot, ::earthSlot, ::airSlot)

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity): ScreenHandler {
        return RuneInfuserGUIDescription(syncId, inv, ScreenHandlerContext.create(player.world, pos), items.size)
    }

    override fun getDisplayName(): Text {
        return TranslatableText(cachedState.block.translationKey)
    }

    override fun getContainerName(): Text {
        return LiteralText("")
    }

    override fun createScreenHandler(syncId: Int, playerInventory: PlayerInventory): ScreenHandler {
        return createMenu(syncId, playerInventory, playerInventory.player)
    }

    override fun getInvStackList(): DefaultedList<ItemStack> {
        return items.toDefaultedList()
    }

    override fun setInvStackList(list: DefaultedList<ItemStack>) {
        items.setToList(list)
    }

    override fun getPropertyDelegate(): PropertyDelegate {
        return delegate
    }

    override fun clear() {
        return super<AInventory>.clear()
    }

    override fun canPlayerUse(player: PlayerEntity): Boolean {
        return super<AInventory>.canPlayerUse(player)
    }

    override fun size(): Int {
        return items.size
    }

    override fun isEmpty(): Boolean {
        return super<AInventory>.isEmpty()
    }

    override fun getStack(slot: Int): ItemStack {
        return super<AInventory>.getStack(slot)
    }

    override fun removeStack(slot: Int, count: Int): ItemStack {
        return super<AInventory>.removeStack(slot, count)
    }

    override fun removeStack(slot: Int): ItemStack {
        return super<AInventory>.removeStack(slot)
    }

    override fun setStack(slot: Int, stack: ItemStack) {
        return super<AInventory>.setStack(slot, stack)
    }

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        Inventories.writeNbt(nbt, items.toDefaultedList())

        return super.writeNbt(nbt)
    }

    override fun readNbt(nbt: NbtCompound) {
        Inventories.readNbt(nbt, items.toDefaultedList())

        super.readNbt(nbt)
    }

    override fun fromClientTag(tag: NbtCompound) {
        Inventories.readNbt(tag, items.toDefaultedList())
    }

    override fun toClientTag(tag: NbtCompound): NbtCompound {
        Inventories.writeNbt(tag, items.toDefaultedList())

        return tag
    }

    override fun getInventory(state: BlockState, world: WorldAccess, pos: BlockPos): SidedInventory {
        return if (this.cachedState == state && this.pos == pos) {
            this
        } else {
            block.getInventory(state, world, pos)
        }
    }

    @Suppress("unused_parameter")
    open fun tick(world: World, pos: BlockPos, state: BlockState) {
        super.tick()

        if (!world.isClient) {
            world.recipeManager.getFirstMatch(RuneRecipe.Type, this, world).asNullable?.let {
                recipeValid = true.asBinaryInt

                if (RuneType.types.any { it.id == runeID }) {
                    runeID = -1
                    resultSlot = it.craft(this).mergeCount(resultSlot)

                    schedule(1) {
                        recipeValid = false.asBinaryInt
                    }
                }
            }
        }
    }

    open fun consumeRecipeIngredients(recipe: RuneRecipe) {
        recipe.inputItems.merge(inputSlotStacks).forEach {
            if (it.first.get()?.item == it.second.get().item &&
                it.first.get() != null) it.second.decrement()
        }
    }

    companion object {
        fun tick(world: World, pos: BlockPos, state: BlockState, entity: RuneInfuserBlockEntity) {
            entity.tick(world, pos, state)
        }
    }
}