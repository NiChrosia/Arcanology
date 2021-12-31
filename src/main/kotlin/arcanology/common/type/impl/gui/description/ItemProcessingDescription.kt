package arcanology.common.type.impl.gui.description

import arcanology.common.Arcanology
import arcanology.common.type.api.gui.description.MachineGuiDescription
import arcanology.common.type.impl.gui.widget.WEnergyBar
import arcanology.common.type.impl.gui.widget.WProcessingBar
import arcanology.common.type.impl.gui.widget.WStorageItemSlot
import arcanology.common.type.impl.world.entity.block.ItemProcessingMachine
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandlerContext

open class ItemProcessingDescription(
    syncId: Int,
    inventory: PlayerInventory,
    context: ScreenHandlerContext
) : MachineGuiDescription<ItemProcessingMachine, ItemProcessingDescription>(
    Arcanology.content.screenHandler.itemProcessingMachine,
    syncId,
    inventory,
    context
) {
    init {
        val root = WGridPanel()
        setRootPanel(root)
        root.setSize(300, 200)
        root.insets = Insets.ROOT_PANEL

        val energyBar = WEnergyBar(machine.energyStorage::amount, machine.energyStorage::capacity)
        root.add(energyBar, 0, 0)

        val input = WStorageItemSlot(machine.itemStorage, 0)
        root.add(input, 100, root.height / 2 - input.height)
        
        val processingBar = WProcessingBar(machine::progress, machine.assemblyType::end)
        root.add(processingBar, calculateWidth(input), root.height / 2 - processingBar.height)
        
        val output = WStorageItemSlot(machine.itemStorage, 1)
        root.add(output, calculateWidth(input, processingBar), root.height / 2 - output.height)

        root.validate(this)
    }
}