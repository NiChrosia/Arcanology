package nichrosia.arcanology.type.content.gui

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import nichrosia.arcanology.type.content.gui.description.SeparatorGUIDescription

@Suppress("MemberVisibilityCanBePrivate", "unused_parameter")
open class SeparatorGUI(
    handler: SeparatorGUIDescription,
    inventory: PlayerInventory,
    title: Text
) : CottonInventoryScreen<SeparatorGUIDescription>(handler, inventory.player)