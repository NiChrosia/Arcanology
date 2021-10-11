package nichrosia.arcanology.type.gui.description

import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.widget.WLabel
import io.github.cottonmc.cotton.gui.widget.WPlainPanel
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.text.TranslatableText
import nichrosia.arcanology.type.gui.widget.WEnergyBar

abstract class MachineGUIDescription(
    type: ScreenHandlerType<*>,
    syncID: Int,
    playerInventory: PlayerInventory,
    context: ScreenHandlerContext,
    inventorySize: Int,
    propertyDelegateSize: Int,
    val titleText: String,
    width: Int = 150,
    height: Int = 190
) : SyncedGuiDescription(type, syncID, playerInventory, getBlockInventory(context, inventorySize), getBlockPropertyDelegate(context, propertyDelegateSize)) {
    val root = WPlainPanel()
    val title = WLabel(TranslatableText(titleText))

    init {
        setRootPanel(root)
        root.setSize(width, height)
        root.insets = Insets.ROOT_PANEL

        title.horizontalAlignment = HorizontalAlignment.CENTER
        root.add(title, width / 2, 0, 18, 18)

        val energyBar = WEnergyBar()
        root.add(energyBar, 0, 2, 18, 84)
    }
}