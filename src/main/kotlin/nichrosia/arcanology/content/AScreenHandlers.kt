package nichrosia.arcanology.content

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.util.Identifier
import nichrosia.arcanology.content.type.Content
import nichrosia.arcanology.type.block.entity.screen.handler.PulverizerScreenHandler

object AScreenHandlers : Content() {
    lateinit var pulverizer: ScreenHandlerType<PulverizerScreenHandler>

    override fun load() {
        pulverizer = ScreenHandlerRegistry.registerSimple(Identifier("arcanology", "")) { syncId, inventory ->
            PulverizerScreenHandler(syncId, inventory, ScreenHandlerContext.EMPTY)
        }
    }
}