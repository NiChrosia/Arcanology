package arcanology.type.api.common.world.block.entity

import io.github.cottonmc.cotton.gui.PropertyDelegateHolder
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.PropertyDelegate
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.text.Text

interface ScreenBlockEntity<S : ScreenHandler> : NamedScreenHandlerFactory, PropertyDelegateHolder {
    val handlerConstructor: (Int, PlayerInventory, ScreenHandlerContext) -> S
    val delegate: PropertyDelegate
    val title: Text

    override fun createMenu(syncID: Int, inventory: PlayerInventory, player: PlayerEntity): ScreenHandler? {
        return handlerConstructor(syncID, inventory, ScreenHandlerContext.create(player.world, (this as BlockEntity).pos))
    }

    override fun getPropertyDelegate(): PropertyDelegate {
        return delegate
    }

    override fun getDisplayName(): Text {
        return title
    }
}