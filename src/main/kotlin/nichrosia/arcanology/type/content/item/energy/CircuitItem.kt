package nichrosia.arcanology.type.content.item.energy

import net.minecraft.item.Item
import nichrosia.arcanology.type.energy.EnergyTier

/** A purely decorational class for all circuits, as they are simply crafting ingredients.
 * Each are comprised of an energy source, a conductor, a switch, and the load.
 *
 * The quality and efficiency of each circuit depends on the material is it made of.
 * For example, a silver wire is far more effective than a copper or aluminum one.
 *
 * Circuits:
 *
 * Rudimentary Circuit ([EnergyTier.T1]): A simple but bulky circuit crafted by hand from aluminum wiring.
 *
 * Basic Circuit ([EnergyTier.T2]): A simple machine made circuit comprised of copper wiring.
 *
 * Advanced Circuit (([EnergyTier.T3])): A more advanced machine-crafted circuit made up of gold wires
 *
 * Hypercircuit ([EnergyTier.T4]): An incredibly powerful circuit meticulously crafted by machinery, made up of pure
 * silver wiring.
 *
 * Supercircuit ([EnergyTier.T5]): An extremely powerful circuit, made up of niobium-titanium alloy, with no
 * energy loss over distance, and incredibly fast transfer rates. */
open class CircuitItem(settings: Settings) : Item(settings)