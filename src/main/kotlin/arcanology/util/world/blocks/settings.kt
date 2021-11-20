@file:Suppress("UNCHECKED_CAST")

package arcanology.util.world.blocks

import net.minecraft.block.AbstractBlock

fun <T : AbstractBlock.Settings> T.requiresToolExtension(): T {
    return requiresTool() as T
}

fun <T : AbstractBlock.Settings> T.strengthExtension(hardness: Float, resistance: Float): T {
    return strength(hardness, resistance) as T
}