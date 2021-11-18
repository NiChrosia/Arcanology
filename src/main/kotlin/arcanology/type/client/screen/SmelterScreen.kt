package arcanology.type.client.screen

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import arcanology.type.common.ui.descriptor.SmelterGuiDescriptor

@Suppress("MemberVisibilityCanBePrivate", "unused_parameter")
open class SmelterScreen(
    handler: SmelterGuiDescriptor,
    inventory: PlayerInventory,
    title: Text
) : CottonInventoryScreen<SmelterGuiDescriptor>(handler, inventory.player)