package arcanology.common.type.api.gui.description

import arcanology.common.type.api.world.entity.block.MachineBlockEntity
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.widget.WWidget
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.ScreenHandlerType

abstract class MachineGuiDescription<E : MachineBlockEntity<E>, G : MachineGuiDescription<E, G>>(
    type: ScreenHandlerType<G>,
    syncId: Int,
    inventory: PlayerInventory,
    context: ScreenHandlerContext
) : SyncedGuiDescription(type, syncId, inventory) {
    open val machine = getMachine<E>(context)

    @Suppress("UNCHECKED_CAST")
    companion object {
        const val spacing = 18 / 3

        fun <E : MachineBlockEntity<E>> getMachine(context: ScreenHandlerContext): E {
            return context.get { world, pos ->
                world.getBlockEntity(pos) as? E
            }.get()
        }

        fun calculateWidth(vararg widgets: WWidget): Int {
            val initialX = widgets.first().x
            val width = widgets.sumOf { it.width }
            // (widgets.size) not subtracted by one due to calculateWidth only being called on second element & onwards
            val spacing = spacing * widgets.size

            return initialX + width + spacing
        }
    }
}