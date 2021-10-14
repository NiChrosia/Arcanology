package nichrosia.arcanology.type.content.gui.description

import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.client.ScreenDrawing
import io.github.cottonmc.cotton.gui.networking.NetworkSide
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking
import io.github.cottonmc.cotton.gui.widget.*
import io.github.cottonmc.cotton.gui.widget.data.Axis
import io.github.cottonmc.cotton.gui.widget.data.InputResult
import io.github.cottonmc.cotton.gui.widget.data.Insets
import io.github.cottonmc.cotton.gui.widget.data.Texture
import net.fabricmc.fabric.api.util.TriState
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.type.content.item.magic.MagicCrystalItem
import nichrosia.arcanology.type.element.Element
import nichrosia.arcanology.type.math.Vec2
import nichrosia.arcanology.type.rune.RuneType
import nichrosia.arcanology.util.add
import nichrosia.arcanology.util.asBoolean
import nichrosia.arcanology.util.hexagon
import nichrosia.arcanology.util.minecraftClient

@Suppress("LeakingThis", "MemberVisibilityCanBePrivate")
open class RuneInfuserGUIDescription(
    syncId: Int,
    playerInventory: PlayerInventory,
    val context: ScreenHandlerContext,
    inventorySize: Int = 7
) : SyncedGuiDescription(
    Registrar.guiDescription.runeInfuser,
    syncId,
    playerInventory,
    getBlockInventory(context, inventorySize),
    getBlockPropertyDelegate(context, 4)
) {
    open val runeX = -6
    open val runeY = -10

    open val runePanelWidth = 72
    open val runePanelHeight = 72

    protected open val root = WPlainPanel().apply {
        setSize(120, 150)
        insets = Insets.ROOT_PANEL
    }

    protected open val runeBackground: WSprite
    protected open val runeSlot: WItemSlot

    protected open val runePanel: WGridPanel
    protected open val runeScrollPanel: WScrollPanel

    val clientNetworking: ScreenNetworking = ScreenNetworking.of(this, NetworkSide.CLIENT)
    val serverNetworking: ScreenNetworking = ScreenNetworking.of(this, NetworkSide.SERVER)

    init {
        setRootPanel(root)

        runeBackground = WSprite(Arcanology.idOf("textures/gui/widget/rune_infuser/rune_background.png"))
        root.add(runeBackground, runeX + 31 + 5, runeY + 31 + 5, 18, 18)

        runeSlot = WItemSlot.of(blockInventory, 0).setInsertingAllowed(false)
        root.add(runeSlot, runeX + 36, runeY + 36)

        val hexagon = hexagon(Vec2(runeX + 36, runeY + 36), 27f)
        Element.elementalValues.forEachIndexed { i, element ->
            val hexagonPos = hexagon[i]

            root.add(WSprite(Arcanology.idOf("textures/gui/widget/rune_infuser/${element.name.lowercase()}_background.png")), hexagonPos, 18, 18)
            root.add(WItemSlot.of(blockInventory, i + 1).setFilter { it.item is MagicCrystalItem && (it.item as MagicCrystalItem).element == element }, hexagonPos, 18, 18)
            root.add(WRuneIngredientRing(
                Arcanology.idOf("textures/gui/widget/rune_infuser/${element.name.lowercase()}_ring_inactive.png"),
                Arcanology.idOf("textures/gui/widget/rune_infuser/${element.name.lowercase()}_ring_active.png")
            ) { true }, hexagonPos, 34, 34)
        }

        runePanel = WGridPanel()
        runePanel.setSize(runePanelWidth, runePanelHeight)

        val box = WBox(Axis.VERTICAL)
        var rowBox = WBox(Axis.HORIZONTAL)

        RuneType.types.forEachIndexed { i, r ->
            val runeWidget = WRune(r.item, r) { runeType ->
                if (propertyDelegate[3].asBoolean) clientNetworking.send(Arcanology.idOf("change_rune_id")) {
                    it.writeInt(runeType.id ?: -1)
                }

                InputResult.PROCESSED
            }

            rowBox.add(runeWidget)

            if ((i + 1) % 5 == 0) {
                box.add(rowBox)

                rowBox = WBox(Axis.HORIZONTAL)
            }
        }

        if (RuneType.types.size % 5 != 0) box.add(rowBox)

        runePanel.add(box, 0, 0)

        runeScrollPanel = object : WScrollPanel(runePanel) {
            init {
                setSize(runePanelWidth * 18, runePanelHeight * 18)

                isScrollingHorizontally = TriState.FALSE
                isScrollingVertically = TriState.TRUE
            }

            override fun paint(matrices: MatrixStack, x: Int, y: Int, mouseX: Int, mouseY: Int) {
                super.paint(matrices, x, y, mouseX, mouseY)

                ScreenDrawing.coloredRect(matrices, x, y, width, height, 0xAAB0B0B0.toInt())
            }
        }

        root.add(runeScrollPanel, 90, 0, runePanelWidth, runePanelHeight)

        root.add(createPlayerInventoryPanel(), 0, 72)

        root.validate(this)

        serverNetworking.receive(Arcanology.idOf("change_rune_id")) {
            propertyDelegate[2] = it.readInt()
        }
    }

    inner class WRune(val item: ItemStack, val runeType: RuneType, val clickListener: (RuneType) -> InputResult) : WWidget() {
        val runeColor: Int
            get() = if (propertyDelegate[3].asBoolean) correctColor else incorrectColor

        init {
            setSize(36, 36)
        }

        fun isHovering(mouseX: Int, mouseY: Int) = mouseX >= 0 && mouseY >= 0 && mouseX < width && mouseY < height

        override fun paint(matrices: MatrixStack, x: Int, y: Int, mouseX: Int, mouseY: Int) {
            super.paint(matrices, x, y, mouseX, mouseY)

            if (isHovering(mouseX, mouseY) || propertyDelegate.get(2) != -1) {
                ScreenDrawing.coloredRect(matrices, x, y, width, height, runeColor)
            }

            minecraftClient.itemRenderer.renderInGui(item, x + 1, y + 1)
        }

        override fun onClick(x: Int, y: Int, button: Int): InputResult {
            return clickListener(runeType)
        }
    }

    inner class WRuneIngredientRing(inactive: Identifier, active: Identifier, val isActive: () -> Boolean) : WWidget() {
        val inactiveTexture = Texture(inactive)
        val activeTexture = Texture(active)

        val texture: Texture
            get() = if (isActive()) activeTexture else inactiveTexture

        override fun paint(matrices: MatrixStack, x: Int, y: Int, mouseX: Int, mouseY: Int) {
            super.paint(matrices, x, y, mouseX, mouseY)

            ScreenDrawing.texturedRect(matrices, x, y, width, height, texture, 0xFFFFFFFF.toInt())
        }
    }

    companion object {
        const val incorrectColor = 0x8cff7259.toInt()
        const val correctColor = 0x8c66ff6b.toInt()
    }
}