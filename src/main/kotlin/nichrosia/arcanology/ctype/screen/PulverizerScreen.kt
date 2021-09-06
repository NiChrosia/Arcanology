package nichrosia.arcanology.ctype.screen

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import nichrosia.arcanology.ctype.gui.description.PulverizerGUIDescription

@Suppress("MemberVisibilityCanBePrivate", "unused_parameter")
open class PulverizerScreen(
    handler: PulverizerGUIDescription,
    inventory: PlayerInventory,
    title: Text
) : CottonInventoryScreen<PulverizerGUIDescription>(handler, inventory.player)