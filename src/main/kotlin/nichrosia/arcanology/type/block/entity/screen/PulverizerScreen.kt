package nichrosia.arcanology.type.block.entity.screen

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import nichrosia.arcanology.type.block.entity.screen.handler.PulverizerScreenHandler

@Suppress("MemberVisibilityCanBePrivate", "unused_parameter")
open class PulverizerScreen(
    handler: PulverizerScreenHandler,
    inventory: PlayerInventory,
    title: Text
) : CottonInventoryScreen<PulverizerScreenHandler>(handler, inventory.player)