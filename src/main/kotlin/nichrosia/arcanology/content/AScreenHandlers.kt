package nichrosia.arcanology.content

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.Identifier
import nichrosia.arcanology.content.type.Content
import nichrosia.arcanology.type.block.entity.screen.handler.PulverizerScreenHandler
import nichrosia.arcanology.type.block.entity.screen.handler.RuneInfuserScreenHandler

object AScreenHandlers : Content {
    lateinit var pulverizer: ScreenHandlerType<PulverizerScreenHandler>
    lateinit var runeInfuser: ScreenHandlerType<RuneInfuserScreenHandler>

    override fun load() {
        pulverizer = ScreenHandlerRegistry.registerSimple(Identifier("arcanology", "pulverizer")) { syncId, inventory ->
            PulverizerScreenHandler(syncId, inventory, ScreenHandlerContext.EMPTY)
        }

        runeInfuser = ScreenHandlerRegistry.registerSimple(Identifier("arcanology", "rune_infuser")) { syncId, inventory ->
            RuneInfuserScreenHandler(syncId, inventory, ScreenHandlerContext.EMPTY)
        }
    }
}