package nichrosia.arcanology.type.content.gui.description

import io.github.cottonmc.cotton.gui.widget.WItemSlot
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.Arcanology.Category.arcanology
import nichrosia.arcanology.type.content.gui.widget.WFluidBar
import nichrosia.arcanology.type.content.gui.widget.WProcessBar
import nichrosia.common.registry.type.Registrar

open class SmelterGuiDescription(
    syncId: Int,
    playerInventory: PlayerInventory,
    context: ScreenHandlerContext,
) : MachineGuiDescription(
    Registrar.arcanology.guiDescription.smelter,
    syncId,
    playerInventory,
    context,
    2,
    7,
    Arcanology.modID.let { "$it.gui.title.smelter" },
    150,
    190
) {
    init {
        root.apply {
            val inputSlot = WItemSlot(blockInventory, 0, 1, 1, false)
            add(inputSlot, 49, 36)

            val progressBar = WProcessBar()
            add(progressBar, 72, 36, 18, 18)

            val outputSlot = WItemSlot(blockInventory, 1, 1, 1, true).apply {
                isInsertingAllowed = false
            }

            add(outputSlot, 99, 36)

            val outputLiquid = WFluidBar { Registry.FLUID[propertyDelegate[6]] }
            add(outputLiquid, 140, 16, WFluidBar.width, WFluidBar.height)

            add(createPlayerInventoryPanel(), 0, 90)

            validate(this@SmelterGuiDescription)
        }
    }
}