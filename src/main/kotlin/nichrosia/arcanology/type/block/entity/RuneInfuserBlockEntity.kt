package nichrosia.arcanology.type.block.entity

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
import nichrosia.arcanology.type.block.entity.screen.handler.RuneInfuserScreenHandler
import nichrosia.arcanology.type.block.entity.type.AInventory
import nichrosia.arcanology.type.rune.base.RuneType

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
                else -> -1
            }
        }

        override fun set(index: Int, value: Int) {
            when(index) {
                0 -> progress = value
                2 -> runeID = value
            }
        }

        override fun size(): Int {
            return 3
        }
    }

    open var progress = 0
    open val maxProgress = 100

    open var runeID = 0

    override val inputSlots = intArrayOf(1)
    override val items: DefaultedList<ItemStack> = DefaultedList.ofSize(2, ItemStack.EMPTY)

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity): ScreenHandler? {
        return RuneInfuserScreenHandler(syncId, inv, ScreenHandlerContext.create(player.world, pos))
    }

    override fun getDisplayName(): Text {
        return TranslatableText(cachedState.block.translationKey)
    }

    override fun getPropertyDelegate(): PropertyDelegate {
        return delegate
    }

    override fun markDirty() {
        super<AInventory>.markDirty()
    }

    @Suppress("unused_parameter")
    fun tick(world: World, pos: BlockPos, state: BlockState) {
        setStack(0, RuneType.types[runeID].item.copy())
    }

    companion object {
        fun tick(world: World, pos: BlockPos, state: BlockState, entity: RuneInfuserBlockEntity) {
            entity.tick(world, pos, state)
        }
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
}