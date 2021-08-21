package nichrosia.arcanology.content.client

import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry
import nichrosia.arcanology.content.ABlockEntityTypes
import nichrosia.arcanology.content.type.Content
import nichrosia.arcanology.type.block.entity.renderer.AltarBlockEntityRenderer

object ABlockEntityRenderers : Content {
    override fun load() {
        BlockEntityRendererRegistry.INSTANCE.register(ABlockEntityTypes.altar, ::AltarBlockEntityRenderer)
    }
}