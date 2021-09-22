package nichrosia.arcanology.ctype.gui

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import nichrosia.arcanology.ctype.gui.description.PulverizerGUIDescription

@Suppress("MemberVisibilityCanBePrivate", "unused_parameter")
open class PulverizerGUI(
    handler: PulverizerGUIDescription,
    inventory: PlayerInventory,
    title: Text
) : CottonInventoryScreen<PulverizerGUIDescription>(handler, inventory.player)