package nichrosia.arcanology.content.client

import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry
import nichrosia.arcanology.content.AScreenHandlers
import nichrosia.arcanology.content.type.Content
import nichrosia.arcanology.type.screen.PulverizerScreen
import nichrosia.arcanology.type.screen.RuneInfuserScreen

object AScreens : Content {
    override fun load() {
        ScreenRegistry.register(AScreenHandlers.pulverizer, ::PulverizerScreen)
        ScreenRegistry.register(AScreenHandlers.runeInfuser, ::RuneInfuserScreen)
    }
}