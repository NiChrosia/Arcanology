package arcanology.common.type.impl.gui.screen

import arcanology.common.type.impl.gui.description.ItemProcessingDescription
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text

open class ItemProcessingScreen(
    description: ItemProcessingDescription,
    inventory: PlayerInventory,
    title: Text
) : CottonInventoryScreen<ItemProcessingDescription>(description, inventory.player, title)