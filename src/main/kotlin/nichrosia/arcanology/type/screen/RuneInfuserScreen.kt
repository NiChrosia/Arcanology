package nichrosia.arcanology.type.screen

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import nichrosia.arcanology.type.screen.description.RuneInfuserGUIDescription

@Suppress("MemberVisibilityCanBePrivate", "unused_parameter")
open class RuneInfuserScreen(
    handler: RuneInfuserGUIDescription,
    inventory: PlayerInventory,
    title: Text
) : CottonInventoryScreen<RuneInfuserGUIDescription>(handler, inventory.player)