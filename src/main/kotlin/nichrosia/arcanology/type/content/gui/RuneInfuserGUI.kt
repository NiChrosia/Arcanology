package nichrosia.arcanology.type.content.gui

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import nichrosia.arcanology.type.content.gui.description.RuneInfuserGUIDescription

@Suppress("MemberVisibilityCanBePrivate", "unused_parameter")
open class RuneInfuserGUI(
    handler: RuneInfuserGUIDescription,
    inventory: PlayerInventory,
    title: Text
) : CottonInventoryScreen<RuneInfuserGUIDescription>(handler, inventory.player)