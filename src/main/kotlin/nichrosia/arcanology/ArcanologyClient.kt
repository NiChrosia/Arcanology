package nichrosia.arcanology

import net.fabricmc.api.ClientModInitializer
import nichrosia.arcanology.content.Content
import nichrosia.arcanology.content.client.*

object ArcanologyClient : ClientModInitializer {
    internal val content = arrayOf<Content>(
        ABlockEntityRenderers
    )

    override fun onInitializeClient() {
        content.forEach(Content::load)
    }
}