package nichrosia.arcanology.type.content.gui

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import nichrosia.arcanology.type.content.gui.description.SeparatorGuiDescription

@Suppress("MemberVisibilityCanBePrivate", "unused_parameter")
open class SeparatorGui(
    handler: SeparatorGuiDescription,
    inventory: PlayerInventory,
    title: Text
) : CottonInventoryScreen<SeparatorGuiDescription>(handler, inventory.player)