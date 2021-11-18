package arcanology.type.common.world.data.tag

import net.minecraft.block.Block
import net.minecraft.util.registry.Registry
import nichrosia.common.identity.ID

interface BlockTag : ContentTag<Block> {
    override fun identify(entry: Block): ID {
        return ID(Registry.BLOCK.getId(entry))
    }

    open class Basic(override val location: ID, vararg values: Block) : BlockTag {
        override val entries: MutableList<Block> = mutableListOf(*values)
    }

    companion object {
        val pickaxeMineable = ID("minecraft", "blocks/mineable/pickaxe")

        fun needsToolLevelX(level: Int): ID {
            return ID("fabric", "blocks/needs_tool_level_$level")
        }
    }
}