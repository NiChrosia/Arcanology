package nichrosia.arcanology.type.screen.description

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
import net.minecraft.util.Identifier
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.content.AScreenHandlers
import nichrosia.arcanology.func.minecraftClient
import nichrosia.arcanology.type.item.magic.MagicCrystalItem
import nichrosia.arcanology.type.rune.base.RuneType

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

    protected open val lightBackground: WSprite
    protected open val lightSlot: WItemSlot

    protected open val voidBackground: WSprite
    protected open val voidSlot: WItemSlot

    protected open val fireBackground: WSprite
    protected open val fireSlot: WItemSlot

    protected open val waterBackground: WSprite
    protected open val waterSlot: WItemSlot

    protected open val earthBackground: WSprite
    protected open val earthSlot: WItemSlot

    protected open val airBackground: WSprite
    protected open val airSlot: WItemSlot

    protected open val runePanel: WGridPanel
    protected open val runeScrollPanel: WScrollPanel

    val clientNetworking: ScreenNetworking = ScreenNetworking.of(this, NetworkSide.CLIENT)
    val serverNetworking: ScreenNetworking = ScreenNetworking.of(this, NetworkSide.SERVER)

    init {
        setRootPanel(root)

        runeBackground = WSprite(Identifier(Arcanology.modID, "textures/gui/widget/rune_infuser/rune_background.png"))
        root.add(runeBackground, runeX + 31 + 5, runeY + 31 + 5, 18, 18)

        runeSlot = WItemSlot.of(blockInventory, 0).setInsertingAllowed(false)
        root.add(runeSlot, runeX + 36, runeY + 36)

        // Elemental crystal slots; light

        lightBackground = WSprite(Identifier(Arcanology.modID, "textures/gui/widget/rune_infuser/light_background.png"))
        root.add(lightBackground, runeX + 36, runeY + 36 + 18 + 9, 18, 18)

        lightSlot = WItemSlot.of(blockInventory, 1).setFilter { it.item is MagicCrystalItem }
        root.add(lightSlot, runeX + 36, runeY + 36 + 18 + 9)

        // void

        voidBackground = WSprite(Identifier(Arcanology.modID, "textures/gui/widget/rune_infuser/void_background.png"))
        root.add(voidBackground, runeX + 36 + 18 + 9, runeY + 36 + 14, 18, 18)

        voidSlot = WItemSlot.of(blockInventory, 2).setFilter { it.item is MagicCrystalItem }
        root.add(voidSlot, runeX + 36 + 18 + 9, runeY + 36 + 14)

        // fire

        fireBackground = WSprite(Identifier(Arcanology.modID, "textures/gui/widget/rune_infuser/fire_background.png"))
        root.add(fireBackground, runeX + 36 + 18 + 9, runeY + 36 - 14, 18, 18)

        fireSlot = WItemSlot.of(blockInventory, 3).setFilter { it.item is MagicCrystalItem }
        root.add(fireSlot, runeX + 36 + 18 + 9, runeY + 36 - 14)

        // water

        waterBackground = WSprite(Identifier(Arcanology.modID, "textures/gui/widget/rune_infuser/water_background.png"))
        root.add(waterBackground, runeX + 36, runeY + 36 - 18 - 9, 18, 18)

        waterSlot = WItemSlot.of(blockInventory, 4).setFilter { it.item is MagicCrystalItem }
        root.add(waterSlot, runeX + 36, runeY + 36 - 18 - 9)

        // earth

        earthBackground = WSprite(Identifier(Arcanology.modID, "textures/gui/widget/rune_infuser/earth_background.png"))
        root.add(earthBackground, runeX + 36 - 18 - 9, runeY + 36 - 14, 18, 18)

        earthSlot = WItemSlot.of(blockInventory, 5).setFilter { it.item is MagicCrystalItem }
        root.add(earthSlot, runeX + 36 - 18 - 9, runeY + 36 - 14)

        // air

        airBackground = WSprite(Identifier(Arcanology.modID, "textures/gui/widget/rune_infuser/air_background.png"))
        root.add(airBackground, runeX + 36 - 18 - 9, runeY + 36 + 14, 18, 18)

        airSlot = WItemSlot.of(blockInventory, 6).setFilter { it.item is MagicCrystalItem }
        root.add(airSlot, runeX + 36 - 18 - 9, runeY + 36 + 14)

        runePanel = WGridPanel()
        runePanel.setSize(runePanelWidth, runePanelHeight)

        val box = WBox(Axis.VERTICAL)
        var rowBox = WBox(Axis.HORIZONTAL)

        RuneType.types.forEachIndexed { i, r ->
            val runeWidget = WRune(r.item, r) { runeType ->
                if (propertyDelegate[3] == 1) clientNetworking.send(Identifier(Arcanology.modID, "change_rune_id")) {
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

        serverNetworking.receive(Identifier(Arcanology.modID, "change_rune_id")) {
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