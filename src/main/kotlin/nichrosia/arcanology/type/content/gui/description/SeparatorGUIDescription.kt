package nichrosia.arcanology.type.content.gui.description

import io.github.cottonmc.cotton.gui.widget.WItemSlot
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandlerContext
import nichrosia.arcanology.Arcanology
import nichrosia.arcanology.registry.Registrar
import nichrosia.arcanology.type.gui.description.MachineGUIDescription
import nichrosia.arcanology.type.gui.widget.WProcessBar

@Suppress("MemberVisibilityCanBePrivate", "JoinDeclarationAndAssignment", "LeakingThis")
open class SeparatorGUIDescription(
    syncId: Int,
    playerInventory: PlayerInventory,
    context: ScreenHandlerContext,
) : MachineGUIDescription(
    Registrar.guiDescription.separator,
    syncId,
    playerInventory,
    context,
    2,
    4,
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

        root.add(createPlayerInventoryPanel(), 0, 90)

        root.validate(this)
    }
}