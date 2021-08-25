package nichrosia.arcanology.type.block.entity.screen.handler

import io.github.cottonmc.cotton.gui.SyncedGuiDescription
import io.github.cottonmc.cotton.gui.client.ScreenDrawing
import io.github.cottonmc.cotton.gui.widget.*
import io.github.cottonmc.cotton.gui.widget.data.*
import net.fabricmc.fabric.api.util.TriState
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.content.AScreenHandlers
import nichrosia.arcanology.type.rune.base.RuneType

@Suppress("LeakingThis", "MemberVisibilityCanBePrivate")
open class RuneInfuserScreenHandler(
    syncId: Int,
    playerInventory: PlayerInventory,
    context: ScreenHandlerContext,
    inventorySize: Int = 7
) : SyncedGuiDescription(
    AScreenHandlers.runeInfuser,
    syncId,
    playerInventory,
    getBlockInventory(context, inventorySize),
    getBlockPropertyDelegate(context, 3)
) {
    open val runeX = -6
    open val runeY = -10

    val runePanelWidth = 72
    val runePanelHeight = 72

    protected val root = WPlainPanel().apply {
        setSize(120, 150)
        insets = Insets.ROOT_PANEL
    }

    protected val runeBackground: WSprite
    protected val runeSlot: WItemSlot

    protected val lightBackground: WSprite
    protected val lightSlot: WItemSlot

    protected val voidBackground: WSprite
    protected val voidSlot: WItemSlot

    protected val fireBackground: WSprite
    protected val fireSlot: WItemSlot

    protected val waterBackground: WSprite
    protected val waterSlot: WItemSlot

    protected val earthBackground: WSprite
    protected val earthSlot: WItemSlot

    protected val airBackground: WSprite
    protected val airSlot: WItemSlot

    protected val runePanel: WGridPanel
    protected val runeScrollPanel: WScrollPanel

    init {
        setRootPanel(root)

        runeBackground = WSprite(Identifier(Arcanology.modID, "textures/gui/widget/rune_infuser/rune_background.png"))
        root.add(runeBackground, runeX + 31 + 5, runeY + 31 + 5, 18, 18)

        runeSlot = WItemSlot.of(blockInventory, 0)
        root.add(runeSlot, runeX + 36, runeY + 36)

        // Elemental crystal slots; light

        lightBackground = WSprite(Identifier(Arcanology.modID, "textures/gui/widget/rune_infuser/light_background.png"))
        root.add(lightBackground, runeX + 36, runeY + 36 + 18 + 9, 18, 18)

        lightSlot = WItemSlot.of(blockInventory, 1)
        root.add(lightSlot, runeX + 36, runeY + 36 + 18 + 9)

        // void

        voidBackground = WSprite(Identifier(Arcanology.modID, "textures/gui/widget/rune_infuser/void_background.png"))
        root.add(voidBackground, runeX + 36 + 18 + 9, runeY + 36 + 14, 18, 18)

        voidSlot = WItemSlot.of(blockInventory, 2)
        root.add(voidSlot, runeX + 36 + 18 + 9, runeY + 36 + 14)

        // fire

        fireBackground = WSprite(Identifier(Arcanology.modID, "textures/gui/widget/rune_infuser/fire_background.png"))
        root.add(fireBackground, runeX + 36 + 18 + 9, runeY + 36 - 14, 18, 18)

        fireSlot = WItemSlot.of(blockInventory, 3)
        root.add(fireSlot, runeX + 36 + 18 + 9, runeY + 36 - 14)

        // water

        waterBackground = WSprite(Identifier(Arcanology.modID, "textures/gui/widget/rune_infuser/water_background.png"))
        root.add(waterBackground, runeX + 36, runeY + 36 - 18 - 9, 18, 18)

        waterSlot = WItemSlot.of(blockInventory, 4)
        root.add(waterSlot, runeX + 36, runeY + 36 - 18 - 9)

        // earth

        earthBackground = WSprite(Identifier(Arcanology.modID, "textures/gui/widget/rune_infuser/earth_background.png"))
        root.add(earthBackground, runeX + 36 - 18 - 9, runeY + 36 - 14, 18, 18)

        earthSlot = WItemSlot.of(blockInventory, 5)
        root.add(earthSlot, runeX + 36 - 18 - 9, runeY + 36 - 14)

        // air

        airBackground = WSprite(Identifier(Arcanology.modID, "textures/gui/widget/rune_infuser/air_background.png"))
        root.add(airBackground, runeX + 36 - 18 - 9, runeY + 36 + 14, 18, 18)

        airSlot = WItemSlot.of(blockInventory, 6)
        root.add(airSlot, runeX + 36 - 18 - 9, runeY + 36 + 14)

        runePanel = WGridPanel()
        runePanel.setSize(runePanelWidth, runePanelHeight)

        val box = WBox(Axis.VERTICAL)
        var rowBox = WBox(Axis.HORIZONTAL)

        RuneType.types.forEachIndexed { i, r ->
            val runeWidget = WRune(r.item, r) {
                propertyDelegate[2] = it.id
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
    }

    open class WRune(val item: ItemStack, val runeType: RuneType, val clickListener: (RuneType) -> Unit) : WWidget() {
        init {
            setSize(36, 36)
        }

        fun isHovering(mouseX: Int, mouseY: Int) = mouseX >= 0 && mouseY >= 0 && mouseX < width && mouseY < height

        override fun paint(matrices: MatrixStack, x: Int, y: Int, mouseX: Int, mouseY: Int) {
            super.paint(matrices, x, y, mouseX, mouseY)

            if (isHovering(mouseX, mouseY)) ScreenDrawing.coloredRect(matrices, x, y, width, height, 0x8c8c8cFF.toInt())

            MinecraftClient.getInstance().itemRenderer.renderInGui(item, x + 1, y + 1)
        }

        override fun onClick(x: Int, y: Int, button: Int): InputResult {
            clickListener(runeType)

            return InputResult.PROCESSED
        }
    }
}