package nichrosia.arcanology.type.id.block

import net.minecraft.block.OreBlock
import net.minecraft.util.Identifier

open class IdentifiedOreBlock(settings: Settings, override val ID: Identifier) : OreBlock(settings), AbstractBlock