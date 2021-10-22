package nichrosia.arcanology.type.id.item

import net.minecraft.item.Item
import net.minecraft.util.Identifier

open class IdentifiedItem(settings: Settings, override val ID: Identifier) : Item(settings), AbstractItem