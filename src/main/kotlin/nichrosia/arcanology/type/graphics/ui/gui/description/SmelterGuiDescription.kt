package nichrosia.arcanology.type.graphics.ui.gui.description

import io.github.cottonmc.cotton.gui.widget.WItemSlot
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.Arcanology.arcanology
import nichrosia.arcanology.type.graphics.ui.gui.widget.WFluidBar
import nichrosia.arcanology.type.graphics.ui.gui.widget.WProcessBar
import nichrosia.common.record.registrar.Registrar

open class SmelterGuiDescription(
    syncId: Int,
    playerInventory: PlayerInventory,
    context: ScreenHandlerContext,
) : MachineGuiDescription(
    Registrar.arcanology.guiDescription.smelter,
    syncId,
    playerInventory,
    context,
    1,
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

            val outputLiquid = WFluidBar { Registry.FLUID[propertyDelegate[6]] }
            add(outputLiquid, 95, 16, WFluidBar.width, WFluidBar.height)

            add(createPlayerInventoryPanel(), 0, 90)

            validate(this@SmelterGuiDescription)
        }
    }
}