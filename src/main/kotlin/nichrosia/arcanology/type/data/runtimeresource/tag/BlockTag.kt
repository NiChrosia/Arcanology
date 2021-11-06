package nichrosia.arcanology.type.data.runtimeresource.tag

import net.minecraft.block.Block
import net.minecraft.util.registry.Registry
import nichrosia.common.identity.ID

interface BlockTag : ContentTag<Block> {
    override fun identify(entry: Block): ID {
        return ID(Registry.BLOCK.getId(entry))
    }

    open class Basic(override val location: ID, override val values: MutableList<Block> = mutableListOf()) : BlockTag

    companion object {
        val pickaxeMineable = ID("minecraft", "blocks/mineable/pickaxe")

        fun needsToolLevelX(level: Int): ID {
            return ID("fabric", "blocks/needs_tool_level_$level")
        }
    }
}