package arcanology.common.type.api.world.entity.block

import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

interface ScreenBlockEntity<T, H : ScreenHandler> : NamedScreenHandlerFactory where T : BlockEntity, T : ScreenBlockEntity<T, H> {
    val handlerConstructor: (Int, PlayerInventory, ScreenHandlerContext) -> H
    val title: Text

    fun getWorld(): World?
    fun getPos(): BlockPos

    override fun createMenu(syncId: Int, inv: PlayerInventory, player: PlayerEntity): H {
        return handlerConstructor(syncId, inv, ScreenHandlerContext.create(getWorld(), getPos()))
    }

    override fun getDisplayName(): Text {
        return title
    }

    fun T.blockName(): TranslatableText {
        return TranslatableText(cachedState.block.translationKey)
    }
}