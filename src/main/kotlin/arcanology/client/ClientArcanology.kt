package arcanology.client

import arcanology.client.content.ClientContent
import net.fabricmc.api.ClientModInitializer
import nucleus.client.builtin.division.ClientModRoot

object ClientArcanology : ClientModRoot<ClientArcanology>("arcanology"), ClientModInitializer {
    override val instance = this
    override val content = ClientContent(instance)

    override fun onInitializeClient() {
        launch()
    }
}