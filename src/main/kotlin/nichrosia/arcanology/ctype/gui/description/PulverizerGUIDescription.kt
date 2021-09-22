package nichrosia.arcanology.ctype.gui.description

import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WItemSlot
import io.github.cottonmc.cotton.gui.widget.WLabel
import io.github.cottonmc.cotton.gui.widget.data.HorizontalAlignment
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.text.TranslatableText
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.type.gui.widget.WEnergyBar
import nichrosia.arcanology.type.gui.widget.WProcessBar

@Suppress("MemberVisibilityCanBePrivate", "JoinDeclarationAndAssignment", "LeakingThis")
open class PulverizerGUIDescription(
    syncId: Int,
    playerInventory: PlayerInventory,
    context: ScreenHandlerContext,
    inventorySize: Int = 2
) : SyncedGuiDescription(
    Registrar.guiDescription.pulverizer,
    syncId,
    playerInventory,
    getBlockInventory(context, inventorySize),
    getBlockPropertyDelegate(context, 4)
) {
    init {
        val root = WGridPanel()
        setRootPanel(root)
        root.setSize(150, 190)
        root.insets = Insets.ROOT_PANEL

        val title = WLabel(TranslatableText("arcanology.gui.title.pulverizer"))
        title.horizontalAlignment = HorizontalAlignment.CENTER
        root.add(title, 2, 0, 1, 1)

        val energyBar = WEnergyBar()
        root.add(energyBar, 0, 0, 1, 4)

        val inputSlot = WItemSlot.of(blockInventory, 0)
        root.add(inputSlot, 3, 2)

        val progressBar = WProcessBar()
        root.add(progressBar, 4, 2, 1, 1)

        val outputSlot = WItemSlot.of(blockInventory, 1)
        outputSlot.isInsertingAllowed = false
        root.add(outputSlot, 5, 2)

        root.add(this.createPlayerInventoryPanel(), 0, 5)

        root.validate(this)
    }
}