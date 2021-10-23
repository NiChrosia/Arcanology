package nichrosia.arcanology.type.content.item.energy

import net.minecraft.item.Item
import net.minecraft.util.Identifier

/** Another purely decorational class to designate what item is what.
 *
 * Available wire types in order of efficiency:
 *
 * Aluminum (T1), copper (T2), gold (T3), silver (T4), and niobium-titanium alloy (T5) for superconductors. */
open class WireItem(settings: Settings) : Item(settings)