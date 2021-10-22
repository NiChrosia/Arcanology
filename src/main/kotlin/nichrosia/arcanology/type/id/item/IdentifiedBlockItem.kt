package nichrosia.arcanology.type.id.item

import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.util.Identifier
import nichrosia.arcanology.type.id.block.AbstractBlock

open class IdentifiedBlockItem<B>(block: B, settings: Settings, override val ID: Identifier) : BlockItem(block, settings), AbstractItem where B : AbstractBlock, B : Block