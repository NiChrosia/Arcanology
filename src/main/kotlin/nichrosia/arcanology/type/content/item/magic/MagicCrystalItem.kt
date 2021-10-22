package nichrosia.arcanology.type.content.item.magic

import net.minecraft.util.Identifier
import nichrosia.arcanology.type.id.item.IdentifiedItem
import nichrosia.arcanology.type.element.Element

/** A purely decorative class for determining whether an item is a crystal */
open class MagicCrystalItem(settings: Settings, ID: Identifier, val element: Element) : IdentifiedItem(settings, ID)