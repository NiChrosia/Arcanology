package arcanology.common.type.impl.screen

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text

@Suppress("UNUSED_PARAMETER") // title is necessary for ScreenRegistry publishing
open class ModularScreen(
    handler: ModularScreenHandler,
    inventory: PlayerInventory,
    title: Text
) : CottonInventoryScreen<ModularScreenHandler>(handler, inventory.player)