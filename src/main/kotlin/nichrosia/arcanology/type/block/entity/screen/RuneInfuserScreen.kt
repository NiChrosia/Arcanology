package nichrosia.arcanology.type.block.entity.screen

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.text.Text
import nichrosia.arcanology.type.block.entity.screen.handler.RuneInfuserScreenHandler

@Suppress("MemberVisibilityCanBePrivate", "unused_parameter")
open class RuneInfuserScreen(
    handler: RuneInfuserScreenHandler,
    inventory: PlayerInventory,
    title: Text
) : CottonInventoryScreen<RuneInfuserScreenHandler>(handler, inventory.player)