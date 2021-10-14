package nichrosia.arcanology.util

import net.minecraft.client.MinecraftClient

/** Utility value for accessing [MinecraftClient] more easily. */
val minecraftClient: MinecraftClient
    get() = MinecraftClient.getInstance()