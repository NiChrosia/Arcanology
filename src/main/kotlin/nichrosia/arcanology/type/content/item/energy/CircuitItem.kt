package nichrosia.arcanology.type.content.item.energy

import net.minecraft.util.Identifier
import nichrosia.arcanology.type.id.item.IdentifiedItem

/** A purely decorational class for all circuits, as they are simply crafting ingredients.
 * Each are comprised of an energy source, a conductor, a switch, and the load.
 *
 * The quality and efficiency of each circuit depends on the material is it made of.
 * For example, a silver wire is far more effective than a copper or aluminum one. */
open class CircuitItem(settings: Settings, ID: Identifier) : IdentifiedItem(settings, ID)