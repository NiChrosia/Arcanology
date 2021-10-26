package nichrosia.arcanology.type.content.gui.description

import io.github.cottonmc.cotton.gui.widget.WItemSlot
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.util.registry.Registry
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.category.ArcanologyCategory.arcanology
import nichrosia.arcanology.type.content.gui.widget.WFluidBar
import nichrosia.arcanology.type.content.gui.widget.WProcessBar
import nichrosia.registry.Registrar

open class SeparatorGuiDescription(
    syncId: Int,
    playerInventory: PlayerInventory,
    context: ScreenHandlerContext,
) : MachineGuiDescription(
    Registrar.arcanology.guiDescription.separator,
    syncId,
    playerInventory,
    context,
    2,
    7,
    "${Arcanology.modID}.gui.title.separator"
) {
    init {
        val inputSlot = WItemSlot(blockInventory, 0, 1, 1, false)
        root.add(inputSlot, 49, 36)

        val progressBar = WProcessBar()
        root.add(progressBar, 72, 36, 18, 18)

        val outputSlot = WItemSlot(blockInventory, 1, 1, 1, true)
        outputSlot.isInsertingAllowed = false
        root.add(outputSlot, 99, 36)

        val outputLiquid = WFluidBar { Registry.FLUID[propertyDelegate[6]] }
        root.add(outputLiquid, 140, 0, 18, 84)

        root.add(createPlayerInventoryPanel(), 0, 90)

        root.validate(this)
    }
}