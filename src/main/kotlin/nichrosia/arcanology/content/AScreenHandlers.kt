package nichrosia.arcanology.content

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.Identifier
import nichrosia.arcanology.content.type.Content
import nichrosia.arcanology.ctype.gui.description.PulverizerGUIDescription
import nichrosia.arcanology.ctype.gui.description.RuneInfuserGUIDescription

object AScreenHandlers : Content {
    lateinit var pulverizer: ScreenHandlerType<PulverizerGUIDescription>
    lateinit var runeInfuser: ScreenHandlerType<RuneInfuserGUIDescription>

    override fun load() {
        pulverizer = ScreenHandlerRegistry.registerSimple(Identifier("arcanology", "pulverizer")) { syncId, inventory ->
            PulverizerGUIDescription(syncId, inventory, ScreenHandlerContext.EMPTY)
        }

        runeInfuser = ScreenHandlerRegistry.registerSimple(Identifier("arcanology", "rune_infuser")) { syncId, inventory ->
            RuneInfuserGUIDescription(syncId, inventory, ScreenHandlerContext.EMPTY)
        }
    }
}