package nichrosia.arcanology.ctype.gui.description

import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.client.ScreenDrawing
import io.github.cottonmc.cotton.gui.networking.NetworkSide
import io.github.cottonmc.cotton.gui.networking.ScreenNetworking
import io.github.cottonmc.cotton.gui.widget.*
import io.github.cottonmc.cotton.gui.widget.data.Axis
import io.github.cottonmc.cotton.gui.widget.data.InputResult
import io.github.cottonmc.cotton.gui.widget.data.Insets
import net.fabricmc.fabric.api.util.TriState
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandlerContext
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.content.AScreenHandlers
import nichrosia.arcanology.func.*
import nichrosia.arcanology.ctype.item.magic.MagicCrystalItem
import nichrosia.arcanology.math.Vec2
import nichrosia.arcanology.type.element.Element
import nichrosia.arcanology.type.rune.RuneType
import kotlin.properties.Delegates

@Suppress("LeakingThis", "MemberVisibilityCanBePrivate")
open class RuneInfuserGUIDescription(
    syncId: Int,
    playerInventory: PlayerInventory,
    context: ScreenHandlerContext,
    inventorySize: Int = 7
) : SyncedGuiDescription(
    AScreenHandlers.runeInfuser,
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

    open var lightBackground by Delegates.notNull<WSprite>()
    open var lightSlot by Delegates.notNull<WItemSlot>()

    open var voidBackground by Delegates.notNull<WSprite>()
    open var voidSlot by Delegates.notNull<WItemSlot>()

    open var fireBackground by Delegates.notNull<WSprite>()
    open var fireSlot by Delegates.notNull<WItemSlot>()

    open var waterBackground by Delegates.notNull<WSprite>()
    open var waterSlot by Delegates.notNull<WItemSlot>()

    open var earthBackground by Delegates.notNull<WSprite>()
    open var earthSlot by Delegates.notNull<WItemSlot>()

    open var airBackground by Delegates.notNull<WSprite>()
    open var airSlot by Delegates.notNull<WItemSlot>()

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

        arrayOf(
            ::lightBackground to "light" toTriple ::lightSlot toQuadruple Element.Light,
            ::voidBackground to "void" toTriple ::voidSlot toQuadruple Element.Void,
            ::fireBackground to "fire" toTriple ::fireSlot toQuadruple Element.Fire,
            ::waterBackground to "water" toTriple ::waterSlot toQuadruple Element.Water,
            ::earthBackground to "earth" toTriple ::earthSlot toQuadruple Element.Earth,
            ::airBackground to "air" toTriple ::airSlot toQuadruple Element.Air
        ).forEachIndexed { i, it ->
            val (backgroundRef, backgroundPathName, slotRef, element) = it
            val hexagonPos = hexagon(Vec2(runeX + 36, runeY + 36), 27f)[i]

            backgroundRef.set(WSprite(Arcanology.idOf("textures/gui/widget/rune_infuser/${backgroundPathName}_background.png")))
            root.add(backgroundRef.get(), hexagonPos, 18, 18)

            slotRef.set(WItemSlot.of(blockInventory, i + 1).setFilter {
                val item = it.item

                item is MagicCrystalItem && item.element == element
            })

            root.add(slotRef.get(), hexagonPos, 18, 18)
        }

        runePanel = WGridPanel()
        runePanel.setSize(runePanelWidth, runePanelHeight)

        val box = WBox(Axis.VERTICAL)
        var rowBox = WBox(Axis.HORIZONTAL)

        RuneType.types.forEachIndexed { i, r ->
            val runeWidget = WRune(r.item, r) { runeType ->
                if (propertyDelegate[3] == 1) clientNetworking.send(Arcanology.idOf("change_rune_id")) {
                    it.writeInt(runeType.id)
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
        init {
            setSize(36, 36)
        }

        fun isHovering(mouseX: Int, mouseY: Int) = mouseX >= 0 && mouseY >= 0 && mouseX < width && mouseY < height

        override fun paint(matrices: MatrixStack, x: Int, y: Int, mouseX: Int, mouseY: Int) {
            super.paint(matrices, x, y, mouseX, mouseY)

            if (isHovering(mouseX, mouseY) || propertyDelegate.get(2) != -1) ScreenDrawing.coloredRect(matrices, x, y, width, height, 0x8c8c8cFF.toInt())

            minecraftClient.itemRenderer.renderInGui(item, x + 1, y + 1)
        }

        override fun onClick(x: Int, y: Int, button: Int): InputResult {
            return clickListener(runeType)
        }
    }
}