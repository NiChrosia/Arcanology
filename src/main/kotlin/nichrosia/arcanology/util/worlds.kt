package nichrosia.arcanology.util

import net.minecraft.world.World

val World.isServer: Boolean
    get() = !isClient