package nichrosia.arcanology.util

import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry
import net.minecraft.client.MinecraftClient

val minecraftClient: MinecraftClient
    get() = MinecraftClient.getInstance()

val blockEntityRendererRegistry: BlockEntityRendererRegistry
    get() = BlockEntityRendererRegistry.INSTANCE