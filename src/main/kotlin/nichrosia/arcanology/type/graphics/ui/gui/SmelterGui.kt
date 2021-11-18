package nichrosia.arcanology.type.graphics.ui.gui

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import nichrosia.arcanology.type.graphics.ui.gui.description.SmelterGuiDescription

@Suppress("MemberVisibilityCanBePrivate", "unused_parameter")
open class SmelterGui(
    handler: SmelterGuiDescription,
    inventory: PlayerInventory,
    title: Text
) : CottonInventoryScreen<SmelterGuiDescription>(handler, inventory.player)