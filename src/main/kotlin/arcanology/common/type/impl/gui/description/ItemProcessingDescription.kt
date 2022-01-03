package arcanology.common.type.impl.gui.description

import arcanology.common.Arcanology
import arcanology.common.type.api.gui.description.MachineGuiDescription
import arcanology.common.type.impl.gui.widget.WProcessingBar
import arcanology.common.type.impl.world.entity.block.ItemProcessingMachine
import arcanology.common.type.impl.world.storage.item.StorageInventory
import io.github.cottonmc.cotton.gui.widget.WPlainPanel
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
    open val storageInventory = machine.itemStorage.let(::StorageInventory)

    init {
        val root = WPlainPanel()
        setRootPanel(root)
        root.setSize(150, 100)
        root.insets = Insets.ROOT_PANEL

        val energyBar = machine.energyStorage.barOf()
        root.add(energyBar, 0, centeredY(root, energyBar), energyBar.width, energyBar.height)

        val input = storageInventory.slotOf(0)
        root.add(input, 35, centeredY(root, input))

        val processingBar = WProcessingBar(machine::progress, machine.assemblyType::end)
        root.add(processingBar, calculateWidth(input), centeredY(root, processingBar))

        val output = storageInventory.slotOf(1)
        root.add(output, calculateWidth(input, processingBar), centeredY(root, output))

        root.validate(this)
    }
}