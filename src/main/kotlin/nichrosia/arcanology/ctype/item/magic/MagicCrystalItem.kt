package nichrosia.arcanology.ctype.item.magic

import net.minecraft.item.Item
import nichrosia.arcanology.type.element.Element

/** A purely decorative class for determining whether an item is a crystal */
open class MagicCrystalItem(settings: Settings, val element: Element) : Item(settings)