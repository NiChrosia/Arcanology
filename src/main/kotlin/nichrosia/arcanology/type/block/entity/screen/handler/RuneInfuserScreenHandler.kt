package nichrosia.arcanology.type.block.entity.screen.handler

import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.client.ScreenDrawing
import io.github.cottonmc.cotton.gui.widget.*
import io.github.cottonmc.cotton.gui.widget.data.Axis
import io.github.cottonmc.cotton.gui.widget.data.InputResult
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.fabricmc.fabric.api.util.TriState
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandlerContext
import nichrosia.arcanology.content.AScreenHandlers
import nichrosia.arcanology.type.rune.base.RuneType

@Suppress("LeakingThis", "MemberVisibilityCanBePrivate")
open class RuneInfuserScreenHandler(
    syncId: Int,
    playerInventory: PlayerInventory,
    context: ScreenHandlerContext,
    inventorySize: Int = 2
) : SyncedGuiDescription(
    AScreenHandlers.runeInfuser,
    syncId,
    playerInventory,
    getBlockInventory(context, inventorySize),
    getBlockPropertyDelegate(context, 2)
) {
    protected val runeSlot: WItemSlot
    protected val crystalSlot: WItemSlot
    protected val runePanel: WGridPanel
    protected val runeScrollPanel: WScrollPanel

    init {
        val root = WGridPanel()
        root.setSize(180, 150)
        root.insets = Insets.ROOT_PANEL

        setRootPanel(root)

        runeSlot = WItemSlot.of(blockInventory, 0)
        root.add(runeSlot, 0, 2)

        crystalSlot = WItemSlot.of(blockInventory, 1)
        root.add(crystalSlot, 0, 3)

        val runePanelWidth = 7
        val runePanelHeight = 4

        runePanel = WGridPanel()
        runePanel.setSize(runePanelWidth * 18, runePanelHeight * 18)

        val box = WBox(Axis.VERTICAL)
        var rowBox = WBox(Axis.HORIZONTAL)

        RuneType.types.forEachIndexed { i, r ->
            val runeWidget = WRune(r.item) {

            }

            rowBox.add(runeWidget)

            if ((i + 1) % 5 == 0) {
                box.add(rowBox)

                rowBox = WBox(Axis.HORIZONTAL)
            }
        }

        if (RuneType.types.size % 5 != 0) box.add(rowBox)

        runePanel.add(box, 0, 0)

        runeScrollPanel = WScrollPanel(runePanel)
        runeScrollPanel.isScrollingHorizontally = TriState.FALSE
        runeScrollPanel.isScrollingVertically = TriState.DEFAULT
        runeScrollPanel.setSize(runePanelWidth * 18, runePanelHeight * 18)

        root.add(runeScrollPanel, 2, 0, runePanelWidth, runePanelHeight)

        root.add(createPlayerInventoryPanel(), 0, 4)

        root.validate(this)
    }

    open class WRune(val item: ItemStack, val clickListener: () -> Unit) : WWidget() {
        init {
            setSize(2 * 18, 2 * 18)
        }

        fun isHovering(mouseX: Int, mouseY: Int) = mouseX >= 0 && mouseY >= 0 && mouseX < width && mouseY < height

        override fun paint(matrices: MatrixStack, x: Int, y: Int, mouseX: Int, mouseY: Int) {
            super.paint(matrices, x, y, mouseX, mouseY)

            if (isHovering(mouseX, mouseY)) ScreenDrawing.coloredRect(matrices, x, y, width, height, 0x8c8c8cff.toInt())

            MinecraftClient.getInstance().itemRenderer.renderInGui(item, x + 1, y + 1)
        }

        override fun onClick(x: Int, y: Int, button: Int): InputResult {
            clickListener()

            return InputResult.PROCESSED
        }
    }
}