package arcanology.common.type.api.gui.description

import arcanology.common.type.api.world.entity.block.AssemblyMachineEntity
import arcanology.common.type.api.world.entity.block.MachineBlockEntity
import arcanology.common.type.impl.gui.widget.WEnergyBar
import arcanology.common.type.impl.gui.widget.WProcessingBar
import assemble.common.type.api.assembly.GradualAssembly
import assemble.common.type.api.assembly.type.gradual.GradualAssemblyType
import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.widget.WItemSlot
import io.github.cottonmc.cotton.gui.widget.WPanel
import io.github.cottonmc.cotton.gui.widget.WWidget
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventory
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.ScreenHandlerType
import team.reborn.energy.api.EnergyStorage

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

        fun <W : WWidget> centeredY(root: WPanel, widget: W): Int {
            return root.height / 2 - widget.height / 2
        }

        fun Inventory.slotOf(index: Int, width: Int = 1, height: Int = 1, big: Boolean = false): WItemSlot {
            return WItemSlot(this, index, width, height, big)
        }

        fun EnergyStorage.barOf(): WEnergyBar {
            return WEnergyBar(this::getAmount, this::getCapacity)
        }
        
        fun <A : GradualAssembly<E>, T : GradualAssemblyType<E, A>, E : AssemblyMachineEntity<E, A, T>> E.processingBarOf(): WProcessingBar {
            return WProcessingBar(this::progress, assemblyType::end)
        }
    }
}