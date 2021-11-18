package arcanology.type.common.ui.descriptor

import arcanology.Arcanology
import arcanology.Arcanology.arcanology
import arcanology.type.common.ui.widget.WFluidBar
import arcanology.type.common.ui.widget.WProcessBar
import io.github.cottonmc.cotton.gui.widget.WItemSlot
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.util.registry.Registry
import nichrosia.common.record.registrar.Registrar

open class SmelterGuiDescriptor(
    syncId: Int,
    playerInventory: PlayerInventory,
    context: ScreenHandlerContext,
) : MachineGuiDescriptor(
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

            validate(this@SmelterGuiDescriptor)
        }
    }
}