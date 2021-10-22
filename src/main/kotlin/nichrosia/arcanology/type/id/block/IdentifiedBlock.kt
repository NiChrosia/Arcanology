package nichrosia.arcanology.type.id.block

import net.minecraft.block.Block
import net.minecraft.util.Identifier

open class IdentifiedBlock(settings: Settings, override val ID: Identifier) : Block(settings), AbstractBlock