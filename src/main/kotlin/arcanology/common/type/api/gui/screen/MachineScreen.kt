package arcanology.common.type.api.gui.screen

import arcanology.common.type.api.gui.description.MachineGuiDescription
import arcanology.common.type.api.world.entity.block.MachineBlockEntity
import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text

open class MachineScreen<E : MachineBlockEntity<E>, G : MachineGuiDescription<E, G>>(
    description: G,
    inventory: PlayerInventory,
    title: Text
) : CottonInventoryScreen<G>(description, inventory.player, title)