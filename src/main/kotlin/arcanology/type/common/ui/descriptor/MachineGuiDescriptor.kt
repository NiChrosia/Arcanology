package arcanology.type.common.ui.descriptor

import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.widget.WLabel
import io.github.cottonmc.cotton.gui.widget.WPlainPanel
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.text.TranslatableText
import arcanology.type.common.ui.widget.WEnergyBar

abstract class MachineGuiDescriptor(
    type: ScreenHandlerType<*>,
    syncID: Int,
    playerInventory: PlayerInventory,
    context: ScreenHandlerContext,
    inventorySize: Int,
    propertyDelegateSize: Int,
    titleText: String,
    val screenWidth: Int,
    val screenHeight: Int
) : SyncedGuiDescription(type, syncID, playerInventory, getBlockInventory(context, inventorySize), getBlockPropertyDelegate(context, propertyDelegateSize)) {
    val root = WPlainPanel()
    val title = WLabel(TranslatableText(titleText))

    init {
        setRootPanel(root)

        root.apply {
            setSize(screenWidth, screenHeight)
            insets = Insets.ROOT_PANEL

            title.horizontalAlignment = HorizontalAlignment.CENTER
            add(title, width / 2, 0, 18, 18)

            val energyBar = WEnergyBar()
            add(energyBar, 0, 2, energyBarWidth, energyBarHeight)
        }
    }

    companion object {
        const val energyBarWidth = 16
        const val energyBarHeight = 84
    }
}