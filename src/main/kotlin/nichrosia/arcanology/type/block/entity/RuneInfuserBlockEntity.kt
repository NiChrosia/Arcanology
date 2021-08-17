package nichrosia.arcanology.type.block.entity

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
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

open class RuneInfuserBlockEntity(
    pos: BlockPos,
    state: BlockState
) : BlockEntity(
    ABlockEntityTypes.runeInfuser,
    pos,
    state
), NamedScreenHandlerFactory, AInventory, PropertyDelegateHolder {
    private val delegate = object : PropertyDelegate {
        override fun get(index: Int): Int {
            return when(index) {
                0 -> progress
                1 -> maxProgress
                else -> -1
            }
        }

        override fun set(index: Int, value: Int) {
            when(index) {
                0 -> progress = value
            }
        }

        override fun size(): Int {
            return 2
        }
    }

    open var progress = 0
    open val maxProgress = 100

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

    fun tick(world: World, pos: BlockPos, state: BlockState) {

    }

    companion object {
        fun tick(world: World, pos: BlockPos, state: BlockState, entity: RuneInfuserBlockEntity) {
            entity.tick(world, pos, state)
        }
    }
}