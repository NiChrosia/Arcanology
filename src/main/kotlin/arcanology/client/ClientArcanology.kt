package arcanology.client

import arcanology.client.content.ClientContent
import net.fabricmc.api.ClientModInitializer
import nucleus.client.builtin.division.ClientModRoot

object ClientArcanology : ClientModRoot<ClientArcanology>("arcanology"), ClientModInitializer {
    override val content = ClientContent(this)

    override fun onInitializeClient() {
        launch(this)
    }
}