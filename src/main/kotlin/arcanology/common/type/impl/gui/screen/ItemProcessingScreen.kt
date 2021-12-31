package arcanology.common.type.impl.gui.screen

import arcanology.common.type.api.gui.screen.MachineScreen
import arcanology.common.type.impl.gui.description.ItemProcessingDescription
import arcanology.common.type.impl.world.entity.block.ItemProcessingMachine
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text

open class ItemProcessingScreen(
    description: ItemProcessingDescription,
    inventory: PlayerInventory,
    title: Text
) : MachineScreen<ItemProcessingMachine, ItemProcessingDescription>(description, inventory, title)