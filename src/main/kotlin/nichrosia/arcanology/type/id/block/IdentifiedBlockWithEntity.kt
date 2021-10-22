package nichrosia.arcanology.type.id.block

import net.minecraft.block.BlockWithEntity
import net.minecraft.util.Identifier
import nichrosia.arcanology.type.id.Identified

abstract class IdentifiedBlockWithEntity(settings: Settings, override val ID: Identifier) : BlockWithEntity(settings), AbstractBlock