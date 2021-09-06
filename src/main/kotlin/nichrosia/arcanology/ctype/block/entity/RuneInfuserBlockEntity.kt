package nichrosia.arcanology.ctype.block.entity

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import nichrosia.arcanology.content.ABlockEntityTypes
import nichrosia.arcanology.func.*
import nichrosia.arcanology.type.properties.block.entity.inventory.ItemSlot
import nichrosia.arcanology.type.recipe.RuneRecipe
import nichrosia.arcanology.type.block.entity.inventory.AInventory
import nichrosia.arcanology.type.rune.RuneType
import nichrosia.arcanology.ctype.gui.description.RuneInfuserGUIDescription
import kotlin.reflect.KMutableProperty0

@Suppress("MemberVisibilityCanBePrivate", "NestedLambdaShadowedImplicitParameter")
open class RuneInfuserBlockEntity(
    pos: BlockPos,
    state: BlockState
) : BlockEntity(
    ABlockEntityTypes.runeInfuser,
    pos,
    state
), NamedScreenHandlerFactory, AInventory, PropertyDelegateHolder, BlockEntityClientSerializable {
    private val delegate = object : PropertyDelegate {
        override fun get(index: Int): Int {
            return when(index) {
                0 -> progress
                1 -> maxProgress
                2 -> runeID
                3 -> recipeValid.asInt
                else -> -1
            }
        }

        override fun set(index: Int, value: Int) {
            when(index) {
                0 -> progress = value
                2 -> runeID = value
                3 -> recipeValid.asInt = value
            }
        }

        override fun size(): Int {
            return 4
        }
    }

    open var progress = 0
    open val maxProgress = 100

    open var runeID = -1
    open var recipeValid = BinaryInt(0)

    override val inputSlots = (1..6).toList().toIntArray()
    override val items: DefaultedList<ItemStack> = DefaultedList.ofSize(7, ItemStack.EMPTY)

    var resultSlot by ItemSlot<RuneInfuserBlockEntity>(0)
    var lightSlot by ItemSlot<RuneInfuserBlockEntity>(1)
    var voidSlot by ItemSlot<RuneInfuserBlockEntity>(2)
    var fireSlot by ItemSlot<RuneInfuserBlockEntity>(3)
    var waterSlot by ItemSlot<RuneInfuserBlockEntity>(4)
    var earthSlot by ItemSlot<RuneInfuserBlockEntity>(5)
    var airSlot by ItemSlot<RuneInfuserBlockEntity>(6)

    val inputSlotStacks: Array<KMutableProperty0<ItemStack>>
        get() = arrayOf(::lightSlot, ::voidSlot, ::fireSlot, ::waterSlot, ::earthSlot, ::airSlot)

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity): ScreenHandler? {
        return RuneInfuserGUIDescription(syncId, inv, ScreenHandlerContext.create(player.world, pos))
    }

    override fun getDisplayName(): Text {
        return TranslatableText(cachedState.block.translationKey)
    }

    override fun getPropertyDelegate(): PropertyDelegate {
        return delegate
    }

    @Suppress("unused_parameter")
    open fun tick(world: World, pos: BlockPos, state: BlockState) {
        if (!world.isClient) {
            world.recipeManager.getFirstMatch(RuneRecipe.type, this, world).asNullable?.let {
                recipeValid.asBoolean = true

                if (RuneType.types.any { it.id == runeID }) {
                    runeID = -1
                    recipeValid.asBoolean = false

                    resultSlot = it.craft(this).mergeCount(resultSlot)
                }
            }
        }
    }

    override fun writeNbt(nbt: NbtCompound): NbtCompound {
        nbt.putInt("arcanologyRuneID", runeID)

        return super.writeNbt(nbt)
    }

    override fun readNbt(nbt: NbtCompound) {
        if (nbt.contains("arcanologyRuneID")) {
            runeID = nbt.getInt("arcanologyRuneID")
        }

        super.readNbt(nbt)
    }

    override fun fromClientTag(tag: NbtCompound) {
        if (tag.contains("arcanologyRuneID")) {
            runeID = tag.getInt("arcanologyRuneID")
        }
    }

    override fun toClientTag(tag: NbtCompound): NbtCompound {
        tag.putInt("arcanologyRuneID", runeID)
        
        return tag
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