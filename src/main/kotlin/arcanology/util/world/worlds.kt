package arcanology.util.world

import net.minecraft.world.World

val World.isServer: Boolean
    get() = !isClient